package com.ruoyi.biz.api.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.biz.config.MySet;
import com.ruoyi.biz.api.service.TokenApiService;
import com.ruoyi.biz.api.util.WxUtils;
import com.ruoyi.biz.domain.IntegralLog;
import com.ruoyi.biz.domain.Qrcode;
import com.ruoyi.biz.domain.User;
import com.ruoyi.biz.param.*;
import com.ruoyi.biz.dto.UserDTO;
import com.ruoyi.biz.service.*;
import com.ruoyi.common.core.domain.ApiResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName : WeChatMiniProgramController
 * @Description : 微信小程序登录
 * @Author : Fly
 * @Date: 2021-01-26 15:11
 */


@RestController
@Slf4j
@RequestMapping("/api/login")
@Api(tags = {"登录接口"})
public class LoginApiController {
    @Autowired
    private TokenApiService tokenApiService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ISmsCodeService smsCodeService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private IQrcodeService qrcodeService;

    @Autowired
    private IPhotoAlbumService photoAlbumService;

    @Autowired
    private IIntegralLogService iIntegralLogService;

    /**
     * 小程序基于code的登入
     *
     * @param loginParam 登入参数
     * @return 服务器端生成token，用于下次快速登入
     */
    @ApiOperation("小程序基于微信code登入")
    @PostMapping
    public ApiResult<UserDTO> login(@RequestBody LoginParam loginParam) {
        JSONObject authInfo = WxUtils.getAuthInfo(loginParam.getCode());
        User user = new User();
        user.setOpenid(authInfo.getString(WxUtils.OPENID));
        List<User> userList = userService.selectUserList(user);

        if (userList.isEmpty() && (StringUtils.isEmpty(loginParam.getEncryptedData()) || StringUtils.isEmpty(loginParam.getIv()))) {
            return ApiResult.error(HttpStatus.HTTP_ACCEPTED, "未授权微信用户信息");
        }

        long expireTime = System.currentTimeMillis() + (int) TimeUnit.MINUTES.toMillis(tokenApiService.getExpireTime());
        if (userList.isEmpty()) {
            JSONObject userInfo = WxUtils.getWxUserInfo(loginParam.getEncryptedData(), loginParam.getIv(), authInfo.getString(WxUtils.SESSION_KEY));
            user.setOpenid(authInfo.getString(WxUtils.OPENID));
            user.setNickname(userInfo.getString(WxUtils.NICK_NAME));
            user.setTokenExpiredDate(new Date(expireTime));
            user.setRegisterTime(new Date());
            user.setLoginTime(new Date());
            user.setAvatar(userInfo.getString(WxUtils.AVATAR_URL));
            user.setOrdIntegral(MySet.ordIntegral);
            userService.insertUser(user);

            // 分享得积分
            if (!StringUtils.isEmpty(loginParam.getUuid())) {
                QrcodeParam qrcodeParam = redisCache.getCacheObject(loginParam.getUuid());
                User userOrder = userService.selectUserById(qrcodeParam.getUserId());
                userOrder.setRewIntegral(userOrder.getRewIntegral() + MySet.rewIntegral);
//                redisCache.deleteObject(loginParam.getUuid());

                // 保存分享记录
                Qrcode qrcode = Qrcode.builder()
                        .userId(userOrder.getId())
                        .rewIntegral(MySet.rewIntegral)
                        .build();
                iIntegralLogService.insertIntegralLog(IntegralLog.builder()
                        .rewIntegral(MySet.rewIntegral)
                        .userId(userOrder.getId())
                        .build());

                qrcodeService.insertQrcode(qrcode);
            }
            user.setToken(tokenApiService.createToken(String.valueOf(user.getId())));
        } else {
            user = userList.get(0);
            user.setToken(tokenApiService.createToken(String.valueOf(user.getId())));
            user.setTokenExpiredDate(new Date(expireTime));
            user.setLoginTime(new Date());
            userService.updateUser(user);
        }
        log.info("登录成功！");
        tokenApiService.setLoginUser(user);
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        userDTO.setUrl(photoAlbumService.getUrl());
        return ApiResult.success(userDTO);
    }


    @ApiOperation("发送手机验证码")
    @GetMapping("/smsCode")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", dataType = "int", example = "13700000000", required = true)
    })
    public ApiResult smsCode(String phone) {
        if (StrUtil.isBlank(phone)){
            return ApiResult.error("手机号不能为空");
        }
        ApiResult ajax = ApiResult.success();
        User user = new User();
        user.setPhone(phone);
        int smsResult = smsCodeService.sendSmsCode(phone);
        if (smsResult == 1) {
            ajax = ApiResult.error("已达到今日发送短信上限");
        } else if (smsResult == 2) {
            ajax = ApiResult.error("发送短信过于频繁");
        }
        return ajax;
    }

    @ApiOperation("注册登录接口")
    @PostMapping("/register")
    public ApiResult<UserDTO> register(@RequestBody @Validated RegisterParam register) {
        if (!smsCodeService.validCode(register.getPhone(), register.getCode())) {
            return ApiResult.error("验证码无效");
        }
        User user = new User();
        user.setPhone(register.getPhone());
        List<User> users = userService.selectUserList(user);
        if (!users.isEmpty()) {
//            user = users.get(0);
            // 重复注册
            return ApiResult.error("该手机号已注册");
        } else {
            user.setRegisterTime(new Date());
            user.setAvatar("https://minip-data.blueasiainfo.com/prod-api/profile/upload/default-avatar.png");
            // 密码加盐保存到数据库
            user.setPassword(SecurityUtils.encryptPassword(register.getPassword()));
            userService.insertUser(user);
            user = userService.selectUserById(user.getId());
        }
        String token = tokenApiService.createToken(String.valueOf(user.getId()));
        return setUserToken(user, token);
    }


//    @ApiOperation("自动登录")
//    @GetMapping
//    public ApiResult<UserDTO> automaticLogin(HttpServletRequest request, AutomaticLoginParam automaticLoginParam) {
//        // 1、校验参数
//        if (StringUtils.isEmpty(automaticLoginParam.getPhone()) || StringUtils.isEmpty(automaticLoginParam.getVersion())) {
//            return ApiResult.error(500, "缺少参数");
//        }
//        // 2、查找用户是否注册过
//        User user = userService.selectUserByPhone(automaticLoginParam.getPhone());
//        if (ObjectUtil.isNotEmpty(user)) {
//            // 3、不为空，注册过，创建token（同一个用户）
//            String token = tokenApiService.createToken(String.valueOf(user.getId()));
//            // 4、更新用户信息到 mysql，redis
//            return setUserToken(user, token);
//        } else {
//            // 5、为空，没注册过，创建用户
//            user = new User();
//            // 生成一个6位的随机字符数字串，进行加密保存
//            user.setPassword(SecurityUtils.encryptPassword(RandomUtil.randomString(6)));
//            user.setRegisterTime(new Date());
//            user.setPhone(automaticLoginParam.getPhone());
//            user.setAvatar("https://minip-data.blueasiainfo.com/prod-api/profile/upload/default-avatar.png");
//            userService.insertUser(user);
//            String token = tokenApiService.createToken(String.valueOf(user.getId()));
//            return setUserToken(user, token);
//        }
//    }

    @ApiOperation("手机密码登录接口")
    @PostMapping("/password")
    public ApiResult<UserDTO> loginPassword(@RequestBody LoginPassword loginPassword) {
        User user = new User();
        user.setPhone(loginPassword.getPhone());
        List<User> users = userService.selectUserList(user);
        if (!users.isEmpty()) {
            user = users.get(0);
        } else {
            return ApiResult.error("没有该手机号");
        }
        if (!SecurityUtils.matchesPassword(loginPassword.getPassword(), user.getPassword())) {
            return ApiResult.error("密码错误");
        }
        String token = tokenApiService.createToken(String.valueOf(user.getId()));
        return setUserToken(user, token);
    }


    private ApiResult<UserDTO> setUserToken(User user, String token) {
        int millis = (int) TimeUnit.MINUTES.toMillis(tokenApiService.getExpireTime());
        Date date = new Date();
        long l = date.getTime() + millis;
        user.setToken(token);
        user.setTokenExpiredDate(new Date(l));
        user.setLoginTime(new Date());
        userService.updateUser(user);
        tokenApiService.setLoginUser(user);
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        return ApiResult.success(userDTO);
    }


}

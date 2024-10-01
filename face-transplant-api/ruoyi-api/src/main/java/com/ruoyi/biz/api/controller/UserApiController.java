package com.ruoyi.biz.api.controller;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageInfo;
import com.ruoyi.biz.api.context.ApiContext;
import com.ruoyi.biz.domain.*;
import com.ruoyi.biz.dto.*;
import com.ruoyi.biz.param.UserUpdateParam;
import com.ruoyi.biz.service.*;
import com.ruoyi.biz.utils.PageUtil;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.ApiResult;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户信息Controller
 *
 * @author zhouzhou
 * @date 2021-06-12
 */
@RestController
@RequestMapping("/api/user")
@Api(tags = "用户接口")
public class UserApiController extends BaseController
{
    @Autowired
    private IUserService userService;

    @Autowired
    private IPhotoAlbumService photoAlbumService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IIntegralLogService integralLogService;

    /**
     * 登录用户信息
     * 教程以及作品
     */
    @ApiOperation("获取登录用户信息")
    @GetMapping("/login/info")
    public ApiResult<UserDTO> loginInfo()
    {
        User user =userService.selectUserById(ApiContext.getCurrentUserId());
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        userDTO.setUrl(photoAlbumService.getUrl());
        return ApiResult.success(userDTO);
    }


    @ApiOperation("修改用户个人信息")
    @PostMapping("/update/user")
    public ApiResult updateUserInfo(@RequestBody UserUpdateParam userUpdate){
        User user = BeanUtil.copyProperties(userUpdate, User.class);
        User dtoUser = userService.selectUserById(ApiContext.getCurrentUserId());
        if (StringUtils.isEmpty(dtoUser.getPassword()) && StringUtils.isNotEmpty(user.getPassword())){
            user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        }
        if (StringUtils.isNotEmpty(dtoUser.getPassword()) && StringUtils.isNotEmpty(user.getPassword()) && !dtoUser.getPassword().equals(user.getPassword()) && !SecurityUtils.matchesPassword(user.getPassword(),dtoUser.getPassword())){
            user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        }
        user.setId(ApiContext.getCurrentUserId());
        ApiContext.setUser(user);
        return ApiResult.toAjax(userService.updateUser(user));
    }


    @ApiOperation("查询用户消费记录")
    @GetMapping("/user/orders")
    public ApiResult<PageInfo<IntegraLogDTO>> userConsume(){
        IntegralLog build = IntegralLog.builder().userId(ApiContext.getCurrentUserId()).build();
        List<IntegralLog> logList = integralLogService.selectIntegralLogList(build);

        return ApiResult.success(PageUtil.convertPageInfo2PageDTO(logList, IntegraLogDTO::convert));
    }

}

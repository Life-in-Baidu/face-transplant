package com.ruoyi.biz.api.controller;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageInfo;
import com.ruoyi.biz.config.MySet;
import com.ruoyi.biz.api.context.ApiContext;
import com.ruoyi.biz.api.util.WxCodeUtil;
import com.ruoyi.biz.domain.MergeList;
import com.ruoyi.biz.domain.Order;
import com.ruoyi.biz.domain.User;
import com.ruoyi.biz.dto.ImageDTO;
import com.ruoyi.biz.dto.MergeListDTO;
import com.ruoyi.biz.dto.OrderDTO;
import com.ruoyi.biz.param.OrderParam;
import com.ruoyi.biz.param.QrcodeParam;
import com.ruoyi.biz.service.IMergeListService;
import com.ruoyi.biz.service.IOrderService;
import com.ruoyi.biz.service.IUserService;
import com.ruoyi.biz.valid.AddGroup;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.ApiResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.UUID;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author hzw
 * @date 2024/6/11
 */
@RestController
@RequestMapping("/api/order")
@Api(tags = "订单接口")
public class OrderApiController extends BaseController {


    @Autowired
    private IOrderService orderService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private IUserService userService;

    @Autowired
    private IMergeListService mergeListService;

    /**
     * 创建订单
     */
    @ApiOperation("创建订单")
    @PostMapping
    public ApiResult<OrderDTO> add(@RequestBody OrderParam param, HttpServletRequest request) {
        Long userId = ApiContext.getCurrentUserId();
        param.setUserId(userId);
        Order order = BeanUtil.copyProperties(param, Order.class);
        return ApiResult.success(orderService.insertOrderParam(order));
    }


    /**
     * web端登录二维码过期时间：10秒
     */
    private static final Integer WEB_LOGIN_QRCODE_EXPIRE_TIME = 10;
    @ApiOperation(value = "分享二维码图片")
    @PostMapping("/web/qrcode")
    public ApiResult<ImageDTO> getWebLoginQrcode(
            @Validated(value = AddGroup.class)
            @RequestBody QrcodeParam qrcodeParam) throws IOException {
        Long userId = ApiContext.getCurrentUserId();
        qrcodeParam.setUserId(userId);

        System.out.println("分享二维码中的信息：" + qrcodeParam);
        String uuid = "uuid_" + UUID.randomUUID().toString().split("-")[1] +
                UUID.randomUUID().toString().split("-")[2] +
                UUID.randomUUID().toString().split("-")[3];
        redisCache.setCacheObject(uuid,qrcodeParam);
        //二维码保存信息时间
        redisCache.expire(uuid,30, TimeUnit.HOURS);
        System.out.println("uuid：" + uuid);
        ImageDTO imageDTO = ImageDTO.builder()
                .base64Code(WxCodeUtil.getConnectionCode2Base64(uuid))
                .flushTime(WEB_LOGIN_QRCODE_EXPIRE_TIME * 1000L)
                .uuid(uuid)
                .build();
        return ApiResult.success(imageDTO);
    }

    @ApiOperation("获取分享图片")
    @GetMapping("/get/qrcode")
    public ApiResult<PageInfo<MergeListDTO>> getRewardIntegral(String uuid){

        if (StringUtils.isEmpty(uuid)) {
            return ApiResult.success();
        }
        QrcodeParam qrcodeParam = redisCache.getCacheObject(uuid);
        System.out.println("获取图片数据：" + qrcodeParam.toString());

        MergeList mergeList = new MergeList();
        mergeList.setUserId(qrcodeParam.getUserId());
        mergeList.setNumber(1);
        List<MergeListDTO> list = mergeListService.selectMergeBefoList(mergeList);
        PageInfo<MergeListDTO> pageInfo = new PageInfo<>(list);
        return ApiResult.success(pageInfo);
    }

}

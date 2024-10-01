package com.ruoyi.biz.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.ContentType;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.ruoyi.biz.config.MySet;
import com.ruoyi.biz.domain.IntegralLog;
import com.ruoyi.biz.domain.Order;
import com.ruoyi.biz.domain.Setmeal;
import com.ruoyi.biz.domain.User;
import com.ruoyi.biz.service.*;
import com.ruoyi.common.enums.OrderStatus;
import com.ruoyi.pay.config.WeChatPayConfig;
import com.ruoyi.pay.utils.WechatPayUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class WechatPayNotifyServiceImpl implements WechatPayNotifyService {

    private static final Map<String, String> SUCCESS_RESULT = new HashMap<String, String>() {{
        put("code", "SUCCESS");
        put("message", "成功");
    }};
    public static final Map<String, String> FAILED_RESULT = new HashMap<String, String>() {{
        put("ceode", "FAIL");
        put("message", "签名错误");
    }};

    @Autowired
    private IOrderService orderService;

    @Autowired
    private ISetmealService setmealService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IIntegralLogService iIntegralLogService;


    @Override
    public Map<String, String> handleNotify(HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info("Wechat pay notify!");
            String result = WechatPayUtils.readData(request);
            // 需要通过证书序列号查找对应的证书，verifyNotify 中有验证证书的序列号
            String plainText = WechatPayUtils.verifyNotify(result, WeChatPayConfig.getApiV3Key());
            log.info("plainText: {}", plainText);
            if (StringUtils.isNotEmpty(plainText)) {
                response.setStatus(200);
                response.setHeader("Content-type", ContentType.JSON.toString());
                response.getOutputStream().write(JSONUtil.toJsonStr(SUCCESS_RESULT).getBytes(StandardCharsets.UTF_8));
                response.flushBuffer();

                // 更新订单状态和用户信息
                String orderNo = JSON.parseObject(plainText).getString(WeChatPayConfig.OUT_TRADE_NO);
                String transactionId = JSON.parseObject(plainText).getString(TRANSACTION_ID);
                paySuccessNotify(orderNo, transactionId);
                return SUCCESS_RESULT;
            } else {
                log.info("校验签名失败：{}", plainText);
                response.setStatus(500);
                return FAILED_RESULT;
            }

        } catch (Exception e) {
            log.error("handle weChat notify failed,input:{}", request, e);
        }
        return FAILED_RESULT;
    }

    /**
     * 支付成功回调的处理方法
     *
     * @param orderNo
     * @param transactionId
     */
    private void paySuccessNotify(String orderNo, String transactionId) {
        // 1.更新订单状态为支付完成
        Order order = orderService.selectByOrderNo(orderNo);
        if (Objects.isNull(order)) {
            log.error("订单不存在！orderNo：{}", orderNo);
        }
        // 更新订单支付状态为支付完成
        order.setPayResult(OrderStatus.PAID.getValue());
        order.setTransactionId(transactionId);
        orderService.updateOrder(order);
        // 更新用户积分
        User user = userService.selectUserById(order.getUserId());
        Setmeal setmeal = setmealService.selectSetmealByMealId(order.getMealId());

        // 更新记录
        IntegralLog build = IntegralLog.builder()
                .payMoney(order.getPayMoney())
                .userId(order.getUserId())
                .build();


        if (Objects.nonNull(setmeal)) {
            build.setOrdIntegral(setmeal.getBonus() + MySet.payValidNum * order.getPayMoney() / 100);
            user.setOrdIntegral(user.getOrdIntegral() + setmeal.getBonus() + MySet.payValidNum * order.getPayMoney() / 100);
        } else {
            build.setOrdIntegral(MySet.payValidNum * order.getPayMoney() / 100);
            user.setOrdIntegral(user.getOrdIntegral() + MySet.payValidNum * order.getPayMoney() / 100);
        }


        iIntegralLogService.insertIntegralLog(build);

        log.info("充值成功");
        userService.updateUser(user);
    }

    /**
     * 延长用户会员有效时间
     *
     * @param validTime 用户当前会员过期时间
     * @param validDay  会员套餐的有效天数
     * @return
     */
    private Date extensionValidTime(Date validTime, Integer validDay) {
        Date currentTime = DateUtil.endOfDay(new Date());
        // 加上套餐天数,如果用户的会员有效日期不为空并且大于当前时间，则等于用户的会员有效日期 + 会员套餐的有效天数，否则等于当前日期 + 会员套餐的有效天数
        if (!Objects.isNull(validTime) && DateUtil.endOfDay(validTime).compareTo(currentTime) >= 0) {
            validTime = DateUtil.offsetDay(DateUtil.endOfDay(validTime), validDay);
        } else {
            validTime = DateUtil.offsetDay(currentTime, validDay);
        }
        return validTime;
    }
}

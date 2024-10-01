package com.ruoyi.pay.service;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.pay.param.PayParam;
import com.ruoyi.pay.param.TransfersParam;

import java.util.Map;

/**
 * 参考：https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_1
 *
 * @author lian.tian
 */
public interface WeChatPayService {

    /**
     * 创建微信支付订单
     *
     * @param payParam 支付参数
     * 参考：https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=9_1
     *
     * @return 返回小程序端拉起微信支付所需参数
     */
    JSONObject pay(PayParam payParam);

    /**
     * 调用微信接口，查询订单状态.
     * 参考：https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=9_2
     *
     * @param tradeOrder 待查询状态的订单
     */
//    OrderStatus queryOrderStatus(TradeOrder tradeOrder);

    /**
     * 关闭订单，同时会调用微信接口进行订单关闭.
     * 参考：https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=9_3
     *
     * @param tradeOrder 订单对象
     */
//    OrderStatus closeOrder(TradeOrder tradeOrder);

    /**
     * 付款,用于向微信用户个人付款
     *
     * @param transfersParam 交易参数
     *
     * 参考： https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=14_2
     * @return 转账结果, return_code字段未SUCCESS/FAIL此字段是通信标识，非交易标识，
     *         交易是否成功需要查看result_code来判断，详细字段信息或报错信息请查看参考上面地址
     */
    Map<String, String> transfers(TransfersParam transfersParam);

}

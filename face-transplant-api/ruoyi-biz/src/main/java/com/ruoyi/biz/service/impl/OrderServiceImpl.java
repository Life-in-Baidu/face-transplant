package com.ruoyi.biz.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.biz.domain.Setmeal;
import com.ruoyi.biz.domain.User;
import com.ruoyi.biz.dto.OrderDTO;
import com.ruoyi.biz.service.ISetmealService;
import com.ruoyi.biz.service.IUserService;
import com.ruoyi.biz.service.WechatPayNotifyService;
import com.ruoyi.common.enums.OrderStatus;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.pay.param.PayParam;
import com.ruoyi.pay.service.WeChatPayService;
import com.ruoyi.pay.utils.OrderGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.biz.mapper.OrderMapper;
import com.ruoyi.biz.domain.Order;
import com.ruoyi.biz.service.IOrderService;

/**
 * 支付记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-06-11
 */
@Slf4j
@Service
public class OrderServiceImpl implements IOrderService 
{
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private IUserService userService;

    @Autowired
    private WeChatPayService weChatPayService;

    @Autowired
    private ISetmealService setmealService;

    /**
     * 查询支付记录
     * 
     * @param orderId 支付记录主键
     * @return 支付记录
     */
    @Override
    public Order selectOrderByOrderId(Long orderId)
    {
        return orderMapper.selectOrderByOrderId(orderId);
    }

    /**
     * 查询支付记录列表
     * 
     * @param order 支付记录
     * @return 支付记录
     */
    @Override
    public List<Order> selectOrderList(Order order)
    {
        return orderMapper.selectOrderList(order);
    }

    /**
     * 新增支付记录
     * 
     * @param order 支付记录
     * @return 结果
     */
    @Override
    public int insertOrder(Order order)
    {
        order.setCreateTime(DateUtils.getNowDate());
        return orderMapper.insertOrder(order);
    }

    /**
     * 修改支付记录
     * 
     * @param order 支付记录
     * @return 结果
     */
    @Override
    public int updateOrder(Order order)
    {
        order.setUpdateTime(DateUtils.getNowDate());
        return orderMapper.updateOrder(order);
    }

    /**
     * 批量删除支付记录
     * 
     * @param orderIds 需要删除的支付记录主键
     * @return 结果
     */
    @Override
    public int deleteOrderByOrderIds(Long[] orderIds)
    {
        return orderMapper.deleteOrderByOrderIds(orderIds);
    }

    /**
     * 删除支付记录信息
     * 
     * @param orderId 支付记录主键
     * @return 结果
     */
    @Override
    public int deleteOrderByOrderId(Long orderId)
    {
        return orderMapper.deleteOrderByOrderId(orderId);
    }

    @Override
    public Order selectByOrderNo(String orderNo) {
        return orderMapper.selectByOrderNo(orderNo);
    }



    @Override
    public OrderDTO insertOrderParam(Order order) {
        // 创建订单号
        String orderNo = OrderGenerator.getInstance().create();
        order.setPayMoney(order.getPayMoney());
        order.setCreateTime(DateUtils.getNowDate());
//        Date newDate = getValidDay(setmeal.getValidDay());
//        order.setValidDay(newDate);
        order.setOrderNo(orderNo);
        order.setPayResult(OrderStatus.PAID.getValue());
        // 2.创建订单
        orderMapper.insertOrder(order);
        log.info("Create order success,id:{},mealId:{}", order.getOrderId(), order.getMealId());
        OrderDTO orderDTO = OrderDTO.convert(order);
        // 3.调用支付接口
        User user = userService.selectUserById(order.getUserId());
        PayParam payParam = PayParam.builder()
                .description("订单描述信息")
                .openId(user.getOpenid())
                .orderNo(orderNo)
                .price(order.getPayMoney())
                .build();
        JSONObject signParams = weChatPayService.pay(payParam);
        log.info("---------signParams: {}", signParams);
        orderDTO.setSignParams(signParams);

        return orderDTO;
    }

    /**
     * 计算订单有效时间
     * @param setMealDate
     * @return
     */
    public Date getValidDay(Integer setMealDate){
        Date date = DateUtils.getNowDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, + setMealDate);
        Date newDate = calendar.getTime();
        return newDate;
    }
}

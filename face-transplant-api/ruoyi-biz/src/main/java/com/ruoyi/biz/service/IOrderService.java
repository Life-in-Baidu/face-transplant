package com.ruoyi.biz.service;

import java.util.List;
import com.ruoyi.biz.domain.Order;
import com.ruoyi.biz.dto.OrderDTO;

/**
 * 支付记录Service接口
 * 
 * @author ruoyi
 * @date 2024-06-11
 */
public interface IOrderService 
{
    /**
     * 查询支付记录
     * 
     * @param orderId 支付记录主键
     * @return 支付记录
     */
    public Order selectOrderByOrderId(Long orderId);

    /**
     * 查询支付记录列表
     * 
     * @param order 支付记录
     * @return 支付记录集合
     */
    public List<Order> selectOrderList(Order order);

    /**
     * 新增支付记录
     * 
     * @param order 支付记录
     * @return 结果
     */
    public int insertOrder(Order order);

    /**
     * 修改支付记录
     * 
     * @param order 支付记录
     * @return 结果
     */
    public int updateOrder(Order order);

    /**
     * 批量删除支付记录
     * 
     * @param orderIds 需要删除的支付记录主键集合
     * @return 结果
     */
    public int deleteOrderByOrderIds(Long[] orderIds);

    /**
     * 删除支付记录信息
     * 
     * @param orderId 支付记录主键
     * @return 结果
     */
    public int deleteOrderByOrderId(Long orderId);


    /**
     * 根据订单流水Id查询订单
     *
     * @param orderNo 订单流水Id
     * @return 订单信息
     */
    public Order selectByOrderNo(String orderNo);


    /**
     * 新增支付记录
     *
     * @param order 支付记录
     * @return 结果
     */
    public OrderDTO insertOrderParam(Order order);
}

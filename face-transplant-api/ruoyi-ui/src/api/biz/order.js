import request from '@/utils/request'

// 查询支付记录列表
export function listOrder(query) {
  return request({
    url: '/biz/order/list',
    method: 'get',
    params: query
  })
}

// 查询支付记录详细
export function getOrder(orderId) {
  return request({
    url: '/biz/order/' + orderId,
    method: 'get'
  })
}

// 新增支付记录
export function addOrder(data) {
  return request({
    url: '/biz/order',
    method: 'post',
    data: data
  })
}

// 修改支付记录
export function updateOrder(data) {
  return request({
    url: '/biz/order',
    method: 'put',
    data: data
  })
}

// 删除支付记录
export function delOrder(orderId) {
  return request({
    url: '/biz/order/' + orderId,
    method: 'delete'
  })
}

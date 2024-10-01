import request from '@/utils/request'

// 查询套餐列表
export function listSetmeal(query) {
  return request({
    url: '/biz/setmeal/list',
    method: 'get',
    params: query
  })
}

// 查询套餐详细
export function getSetmeal(mealId) {
  return request({
    url: '/biz/setmeal/' + mealId,
    method: 'get'
  })
}

// 新增套餐
export function addSetmeal(data) {
  return request({
    url: '/biz/setmeal',
    method: 'post',
    data: data
  })
}

// 修改套餐
export function updateSetmeal(data) {
  return request({
    url: '/biz/setmeal',
    method: 'put',
    data: data
  })
}

// 删除套餐
export function delSetmeal(mealId) {
  return request({
    url: '/biz/setmeal/' + mealId,
    method: 'delete'
  })
}

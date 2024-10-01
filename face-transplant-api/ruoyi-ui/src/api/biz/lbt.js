import request from '@/utils/request'

// 查询轮播图列表
export function listLbt(query) {
  return request({
    url: '/biz/lbt/list',
    method: 'get',
    params: query
  })
}

// 查询轮播图详细
export function getLbt(id) {
  return request({
    url: '/biz/lbt/' + id,
    method: 'get'
  })
}

// 新增轮播图
export function addLbt(data) {
  return request({
    url: '/biz/lbt',
    method: 'post',
    data: data
  })
}

// 修改轮播图
export function updateLbt(data) {
  return request({
    url: '/biz/lbt',
    method: 'put',
    data: data
  })
}

// 删除轮播图
export function delLbt(id) {
  return request({
    url: '/biz/lbt/' + id,
    method: 'delete'
  })
}

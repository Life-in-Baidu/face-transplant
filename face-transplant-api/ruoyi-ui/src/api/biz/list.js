import request from '@/utils/request'

// 查询融合组列表
export function listList(query) {
  return request({
    url: '/biz/list/list',
    method: 'get',
    params: query
  })
}

// 查询融合组详细
export function getList(id) {
  return request({
    url: '/biz/list/' + id,
    method: 'get'
  })
}

// 新增融合组
export function addList(data) {
  return request({
    url: '/biz/list',
    method: 'post',
    data: data
  })
}

// 修改融合组
export function updateList(data) {
  return request({
    url: '/biz/list',
    method: 'put',
    data: data
  })
}

// 删除融合组
export function delList(id) {
  return request({
    url: '/biz/list/' + id,
    method: 'delete'
  })
}

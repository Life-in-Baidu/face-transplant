import request from '@/utils/request'

// 查询积分变动日志列表
export function listLog(query) {
  return request({
    url: '/biz/log/list',
    method: 'get',
    params: query
  })
}

// 查询积分变动日志详细
export function getLog(id) {
  return request({
    url: '/biz/log/' + id,
    method: 'get'
  })
}

// 新增积分变动日志
export function addLog(data) {
  return request({
    url: '/biz/log',
    method: 'post',
    data: data
  })
}

// 修改积分变动日志
export function updateLog(data) {
  return request({
    url: '/biz/log',
    method: 'put',
    data: data
  })
}

// 删除积分变动日志
export function delLog(id) {
  return request({
    url: '/biz/log/' + id,
    method: 'delete'
  })
}

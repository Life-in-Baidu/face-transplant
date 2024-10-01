import request from '@/utils/request'

// 查询目录列列表
export function listDirectory(query) {
  return request({
    url: '/biz/directory/list',
    method: 'get',
    params: query
  })
}

// 根据上级目录id查询目录列表
export function getDirelist(id) {
  return request({
    url: '/biz/directory/getList/' + id,
    method: 'get',
  })
}

// 查询目录列详细
export function getDirectory(id) {
  return request({
    url: '/biz/directory/' + id,
    method: 'get'
  })
}

// 新增目录列
export function addDirectory(data) {
  return request({
    url: '/biz/directory',
    method: 'post',
    data: data
  })
}

// 修改目录列
export function updateDirectory(data) {
  return request({
    url: '/biz/directory',
    method: 'put',
    data: data
  })
}

// 删除目录列
export function delDirectory(id) {
  return request({
    url: '/biz/directory/' + id,
    method: 'delete'
  })
}

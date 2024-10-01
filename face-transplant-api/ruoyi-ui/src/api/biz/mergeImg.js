import request from '@/utils/request'

// 查询融合图片列表
export function listMergeImg(query) {
  return request({
    url: '/biz/mergeImg/list',
    method: 'get',
    params: query
  })
}

// 查询融合图片详细
export function getMergeImg(id) {
  return request({
    url: '/biz/mergeImg/' + id,
    method: 'get'
  })
}

// 新增融合图片
export function addMergeImg(data) {
  return request({
    url: '/biz/mergeImg',
    method: 'post',
    data: data
  })
}

// 修改融合图片
export function updateMergeImg(data) {
  return request({
    url: '/biz/mergeImg',
    method: 'put',
    data: data
  })
}

// 删除融合图片
export function delMergeImg(id) {
  return request({
    url: '/biz/mergeImg/' + id,
    method: 'delete'
  })
}

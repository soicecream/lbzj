import request from '@/utils/request'


//page和limit作为路由地址发送，不对后面查询的字段做修改，便于后端对数据库进行查询，减少冗余
export function fetchProblemsList(page, limit, listQuery) {
  return request({
    url: `/problem/pageProblemsCondition/${page}/${limit}`,
    method: 'post',
    data : listQuery
  })
}

export function getAdminprolist(page, limit, listQuery) {
  return request({
    url: `/problem/adminprolist/${page}/${limit}`,
    method: 'post',
    data : listQuery
  })
}

export function getFile(id) {
  return request({
    url: `/problem/getFile/${id}`,
    method: 'get',
  })
}


export function fetchProblem(id) {
  return request({
    url: `/problem/getProblemById/${id}`,
    method: 'get'
  })
}

export function updatePro(pro) {
  return request({
    url: `/problem/updatePro`,
    method: 'post',
    data : pro
  })
}

export function addPro(pro) {
  return request({
    url: `/problem/addPro`,
    method: 'post',
    data : pro
  })
}

export function delPro(ids) {
  return request({
    url: `/problem/delPro`,
    method: 'post',
    data:ids
  })
}
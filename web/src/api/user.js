import request from '@/utils/request'


export function login(data) {
  return request({
    url: '/login',
    method: 'post',
    data:data
  })
}

export function logout() {
  return request({
    url: '/logout',
    method: 'post'
  })
}

export function getusers( current, limit, userQuery) {
  return request({
    url: `/users/pageuser/${current}/${limit}`,
    method: 'post',
    data:userQuery
  })
}

export function getusersPro( userid) {
  return request({
    url: `/users/getuserPro/${userid}`,
    method: 'post',
  })
}

export function getuser(id) {
  return request({
    url: `/users/getuser/${id}`,
    method: 'get',
  })
}

export function adduser(user) {
  return request({
    url: `/users/adduser`,
    method: 'post',
    data:user
  })
}

export function deluser(ids) {
  return request({
    url: `/users/deluser`,
    method: 'post',
    data:ids
  })
}

export function updateuser(user) {
  return request({
    url: `/users/updateuser`,
    method: 'post',
    data:user
  })
}

export function formuser(user,password) {
  return request({
    url: `/users/formuser/${password}`,
    method: 'post',
    data:user
  })
}





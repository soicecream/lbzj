import request from "@/utils/request";

export function pagerole( current, limit, userQuery) {
    return request({
        url: `/role/pagerole/${current}/${limit}`,
        method: 'post',
        data:userQuery
    })
}

export function adminpagerole( current, limit, userQuery) {
    return request({
        url: `/role/adminpageuser/${current}/${limit}`,
        method: 'post',
        data:userQuery
    })
}


export function getrole() {
    return request({
        url: `/role/getRole`,
        method: 'get',
    })
}

export function getroles() {
    return request({
        url: `/role/getRoles`,
        method: 'get',
    })
}

export function getrolemenu(id) {
    return request({
        url: `/role/getRoleMenu/${id}`,
        method: 'get',
    })
}

export function addrole(role) {
    return request({
        url: `/role/addRole`,
        method: 'post',
        data: role
    })
}

export function uprole(role) {
    return request({
        url: `/role/upRole`,
        method: 'post',
        data: role
    })
}

export function uproles(roleid,status) {
    return request({
        url: `/role/upRoleS/${roleid}/${status}`,
        method: 'post',
    })
}

export function delrole(id) {
    return request({
        url: `/role/delRole`,
        method: 'post',
        data: id
    })
}

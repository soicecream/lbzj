import request from "@/utils/request";

export function pageuserrole( current, limit, userQuery) {
    return request({
        url: `/userrole/pageUserRole/${current}/${limit}`,
        method: 'post',
        data:userQuery
    })
}


export function addusrerole(userrole) {
    return request({
        url: `/userrole/addUserRole`,
        method: 'post',
        data:userrole
    })
}

export function delusrerole(userids) {
    return request({
        url: `/userrole/delUserRole`,
        method: 'post',
        data:userids
    })
}

export function upusrerole(userids) {
    return request({
        url: `/userrole/upUserRole`,
        method: 'post',
        data:userids
    })
}

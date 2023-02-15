import request from '@/utils/request'


//page和limit作为路由地址发送，不对后面查询的字段做修改，便于后端对数据库进行查询，减少冗余
export function fetchProblemsList(page, limit, listQuery) {
    return request.post(`/problem/pageProblemsCondition/${page}/${limit}`, listQuery)
}

export function fetchProblem(id) {
    return request.get(`/problem/getProblemById/${id}`)
}

export function fetchAdminProblem(id) {
    return request.get(`/problem/admin/getProblemById/${id}`)
}

export function getAdminprolist(page, limit, listQuery) {
    return request({
        url: `/problem/adminprolist/${page}/${limit}`,
        method: 'post',
        data: listQuery
    })
}

export function getFile(id) {
    return request({
        url: `/problem/getFile/${id}`,
        method: 'get',
    })
}


export function updatePro(pro) {
    return request({
        url: `/problem/updatePro`,
        method: 'post',
        data: pro
    })
}

export function changEnable(data) {
    return request.post('/problem/chang/enable', data)
}

export function addPro(pro) {
    return request({
        url: `/problem/addPro`,
        method: 'post',
        data: pro
    })
}

export function insertOrUpdate(problem) {
    return request({
        url: `/problem/`,
        method: 'post',
        data: problem,
    })
}

export function uploadSampleFile(data) {
    return request.post('/problem/upload/sample', data)
}

export function downloadSample(id) {
    return request.get(`problem/download/sample/${id}`)
}

export function delPro(ids) {
    return request({
        url: `/problem/delPro`,
        method: 'post',
        data: ids
    })
}

export function uploadVideo(problemId, data) {
    return request.post(`/problem/upload/video/${problemId}`, data)
}

export function getVideo(problemId) {
    return request.post(`/problem/get/video/${problemId}`)
}

export function adminGetVideo(problemId) {
    return request.get(`/problem/admin/get/video/${problemId}`)
}

export function deleteVideo(problemId) {
    return request.post(`/problem/delete/video/${problemId}`)
}

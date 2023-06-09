import request from "@/utils/request";

function getAllList() {
    return request.get('/tags/allTags')
}

function getPageList(current, limit, data) {
    return request.post(`/tags/get/page/${current}/${limit}`, data)
}

function getProblemTags(id) {
    return request.get(`/problem/tags/getProblem/{id}`)
}


function deleteByIds(data) {
    return request.post('/tags/deleteIds', data)
}

export default {
    getAllList: getAllList,
    getPageList: getPageList,
    getProblemTags: getProblemTags,
    deleteByIds: deleteByIds,
}
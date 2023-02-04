import request from "@/utils/request";

function getAllList() {
    return request.get('/tags/allTags')
}

function getProblemTags(id) {
    return request.get(`/problem/tags/getProblem/{id}`)
}

function createOrUpdate(data) {
    return request.post('/tags/createOrUpdate', data)
}

function deleteByIds(data) {
    return request.post('/tags/deleteIds', data)
}

export default {
    getAllList: getAllList,
    getProblemTags: getProblemTags,
    createOrUpdate: createOrUpdate,
    deleteByIds: deleteByIds,
}
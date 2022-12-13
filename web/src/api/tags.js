import request from "@/utils/request";

function getAllList() {
    return request.get("/tags/allTags")
}

function createOrUpdate(data) {
    return request.post('/tags/createOrUpdate', data)
}

export default {
    getAllList: getAllList,
    createOrUpdate: createOrUpdate,
}
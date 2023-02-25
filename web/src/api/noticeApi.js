import request from "@/utils/request";

function getPage(pageNum, pageSize, data) {
    return request.post(`/notice/admin/get/page/${pageNum}/${pageSize}`, data)
}

function createOrUpdate(data) {
    return request.post('/notice/createOrUpdate', data)
}

function deleteByIds(data) {
    return request.post('/notice/delete/ids', data)
}

function getHomeNotice() {
    return request.post('/notice/get/home')
}

function getInformation(noticeId) {
    return request.post(`/notice/get/${noticeId}`)
}

function getPageList(pageNum, pageSize, data) {
    return request.post(`/notice/get/page/list/${pageNum}/${pageSize}`, data)
}

export default {
    getPageList: getPageList,
    getInformation: getInformation,
    getHomeNotice: getHomeNotice,
    deleteByIds: deleteByIds,
    createOrUpdate: createOrUpdate,
    getPage: getPage,
}
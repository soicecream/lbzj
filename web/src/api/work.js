import request from "@/utils/request";

export function getWorkList(page, limit) {
    return request({
        url: `/work/pagework/${page}/${limit}`,
        method: 'post',
    })
}

export function getWorkid(problemid) {
    return request({
        url: `/work/pageworkid/${problemid}`,
        method: 'get',
    })
}

export function getRankWork(workid){
    return request({
        url: `/work/getworkrank/${workid}`,
        method: 'get',
    })
}

export function getProblemid(workid){
    return request({
        url: `/work/getproblemid/${workid}`,
        method: 'get',
    })
}

export function addWork(work){
    return request({
        url: `/work/addwork`,
        method: 'post',
        data:work
    })
}

export function updateWork(work){
    return request({
        url: `/work/updatework`,
        method: 'post',
        data:work
    })
}

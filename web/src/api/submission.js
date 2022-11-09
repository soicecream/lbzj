import request from '@/utils/request'

//page和limit作为路由地址发送，不对后面查询的字段做修改，便于后端对数据库进行查询，减少冗余
export function fetchSubmissionsList(page, limit, listQuery) {
  return request({
    url: `/submission/pageSubmissionsCondition/${page}/${limit}`,
    method: 'post',
    data : listQuery
  })
}


export function saveSubmission(solutionAndSourceCode) {
  return request({
    url: '/submission/saveSubmission',
    method: 'post',
    data : solutionAndSourceCode
  })
}

const getters={
    roles:state=> state.user.roles,
    permissions:state=>state.user.permissions,
    topbarRouters:state=>state.permission.topbarRouters
}

export default getters

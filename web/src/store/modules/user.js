import {getInfo} from "@/api/user";

const user = {
    state: {
        roles: [],
        permissions: []
    },

    mutations: {
        SET_ROLES: (state, roles) => {
            state.roles = roles
        },
        SET_PERMISSIONS: (state, permissions) => {
            state.permissions = permissions
        }
    },

    actions: {
        // 获取用户信息
        GetInfo({ commit, state }) {
            return new Promise((resolve, reject) => {
                getInfo().then(res => {
                        commit('SET_ROLES', res.data.roles)
                        commit('SET_PERMISSIONS', res.data.permissions)
                    resolve(res)
                }).catch(error => {
                    reject(error)
                })
            })
        },

    }
}

export default user

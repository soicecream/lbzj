import Vue from 'vue'
import Vuex from 'vuex'
import user from "@/store/modules/user";
import getters from "@/store/getters";
import permission from "@/store/modules/permission";

Vue.use(Vuex)

const store = new Vuex.Store({
    modules: {
        user,
        permission
    },
    getters
})

export default store

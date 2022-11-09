import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)

export default new Vuex.Store({
    state: { //定义基础的数据
        flag: false,
        num: 10
    },
    mutations: { //同步修改state数据的方法
        //数字加5
        changeSum: (state, params) => { //params是传递过来的入参
            state.num = state.num + params
            console.log(state.num)
        },

        //是否展示图片
        showImg: (state) => {
            state.flag = !state.flag
        },

        //同步执行打印方法
        asyncFun() {
            console.log(888)
        }
    },
    actions: {
        asyncFun(context) {
            setTimeout(() => {
                context.commit("asyncFun"); //调用mutations内的同步方法
            }, 2000);
            console.log("我比asyncFun提前执行");
        }
    }
})

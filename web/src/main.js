import Vue from 'vue'
import App from './App.vue'


import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import store from "@/store/work"


import VueDPlayer from "vue-dplayer";
import "vue-dplayer/dist/vue-dplayer.css";



import router from './router'

Vue.use(ElementUI)
Vue.use(VueDPlayer);

Vue.config.productionTip = false // 作为产品时，减少不必要的警告提示



new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App),
}).$mount('#app')

import Vue from 'vue'
import App from './App.vue'


import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

import directive from './directive'

Vue.use(ElementUI)


import store from "@/store/work"


import VueDPlayer from "vue-dplayer";
import "vue-dplayer/dist/vue-dplayer.css";
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

Vue.use(mavonEditor)
Vue.use(VueDPlayer);
Vue.use(directive)

import router from './router'


// 复制到剪切板
import Clipboard from 'v-clipboard'

Vue.use(Clipboard)


// 处理渲染
// import Fragment from 'vue-fragment'
//
// Vue.use(Fragment.Plugin)



Vue.config.productionTip = false // 作为产品时，减少不必要的警告提示


new Vue({
    el: '#app', router, store, render: h => h(App),
}).$mount('#app')

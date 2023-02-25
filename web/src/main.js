import Vue from 'vue'
import App from './App.vue'

import store from "@/store/work"
import router from './router'

import './assets/gloable.css'


import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
Vue.use(ElementUI)

import directive from './directive'
Vue.use(directive)


import VueDPlayer from "vue-dplayer";
import "vue-dplayer/dist/vue-dplayer.css";
Vue.use(VueDPlayer);

import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
Vue.use(mavonEditor)

import Md_Katex from '@iktakahiro/markdown-it-katex'
Vue.prototype.$markDown = mavonEditor.mavonEditor.getMarkdownIt().use(Md_Katex) // 挂载到vue

import highlight from '@/common/highlight'
Vue.use(highlight) // 代码高亮

import Katex from '@/common/katex'
Vue.use(Katex)  // 数学公式渲染

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

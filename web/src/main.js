import Vue from 'vue'
import App from './App.vue'


import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

Vue.use(ElementUI)


import store from "@/store/work"


import VueDPlayer from "vue-dplayer";
import "vue-dplayer/dist/vue-dplayer.css";

Vue.use(VueDPlayer);


import router from './router'


// 复制到剪切板
import Clipboard from 'v-clipboard'

Vue.use(Clipboard)


Vue.config.productionTip = false // 作为产品时，减少不必要的警告提示


new Vue({
    el: '#app', router, store, render: h => h(App),
}).$mount('#app')

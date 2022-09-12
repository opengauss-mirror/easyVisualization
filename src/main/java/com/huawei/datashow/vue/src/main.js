import Vue from 'vue'

import App from './App.vue'

import ElementUI from 'element-ui';

import 'element-ui/lib/theme-chalk/index.css';

import VueRouter from 'vue-router';

import router from './router'

import store from './store/index'

import VueI18n from 'vue-i18n'

import elementZH from 'element-ui/lib/locale/lang/zh-CN'

import elementEN from 'element-ui/lib/locale/lang/en'

import local_en from '@/assets/language/local_en.json'

import local_zh from '@/assets/language/local_zh.json'


Vue.config.productionTip = false

Vue.use(VueI18n)

Vue.use(VueRouter)

const i18n = new VueI18n({
    locale: sessionStorage.getItem("lang") || 'zh',
    messages:{
      zh:{
        goodday:'have a nice day',
        ...local_zh,
        ...elementZH
    },

    en:{
      goodday:'have a good day',
      ...local_en,
      ...elementEN
      }

  }

})

Vue.use(ElementUI, {
  i18n:(key, value) => i18n.t(key, value)
})

new Vue({
  i18n,
  render: h => h(App),
  store:store,
  router: router,
  mounted() {},
}).$mount('#app')

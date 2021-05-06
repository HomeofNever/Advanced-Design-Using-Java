/**
 * App Main Entry
 * If you want to add more third-party library,
 * please import them in a separate folder and 
 * add in index.js
 */
import './config'
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

export default new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
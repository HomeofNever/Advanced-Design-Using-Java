import Vue from 'vue'
import axios from 'axios'
import store from '@/store'
import v from '@/main'

axios.interceptors.request.use((config) => {
  config.baseURL = "http://localhost:8080"
  let username = store.getters['auth/getUsername']
  let password = store.getters['auth/getPassword']
  if (username && password) {
    // Authentication Authorization
    config.auth = {
      username,
      password
    }
  }
  return config
}, function (error) {
  // Do something with request error
  return Promise.reject(error)
})

axios.interceptors.response.use((response) => {
  return response
}, function (error) {
  if (error.response.status === 401) {
    // Token expired
    // Clear out and redirect to login
    store.dispatch('auth/reset')
  }

  // error displaying
  let err = error.response.data.message
  if (err) {
    v.$toasted.error(err)
  }

  return Promise.reject(error)
})

Vue.prototype.$axios = axios

export default axios

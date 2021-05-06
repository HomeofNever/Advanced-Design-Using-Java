import router from '@/router'
import Vue from '@/main'

export default {
  userLogin ({ commit }, { username, password }) {
    commit('setUsername', username)
    commit('setPassword', password)
    return Vue.$axios.get("/user/me")
      .then(user => {
        console.log(user)
        Vue.$toasted.success('Redirecting to dashboard...')
        router.push({ name: 'DashBoard' })
      })
      .catch(() => {
        Vue.$toasted.error('Login Failed, please check your username/password combination!')
      })
  },
  userJoin ({ commit }, { username, password }) {
    return Vue.$axios.post("/user/register", {
      username, password
    })
      .then(user => {
        console.log(user)
        Vue.$toasted.success('Register successfully!')
        router.push({ name: 'Login' })
      })
      .catch(() => {
        Vue.$toasted.error('Register Failed')
      })
  },
  userSignOut ({ commit }) {
    commit('setUsername', null)
    commit('setPassword', null)
    Vue.$toasted.success('Logout Successfully.')
    router.push({ name: 'Home' })
  },
  reset({ commit }) {
    commit('setUsername', null)
    commit('setPassword', null)
    Vue.$toasted.error('Login credential expired.')
    router.push({ name: 'Login' })
  }
}

import router from '@/router'
import Vue from '@/main'

export default {
  getUser({ commit }) {
    return Vue.$axios.get("/user/me")
      .then(data => {
        console.log(data)
        commit('updateUser', data.data)
      })
  },
  updateUser ({ commit, getters }, { displayName, status}) {
    return Vue.$axios.post('/user/update', {
      displayName, status
    }).then(() => {
      commit('updateUser', {
        ...(getters.getUser),
        displayName, 
        status
      })
    })
  }
}

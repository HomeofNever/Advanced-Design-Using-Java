import router from '@/router'
import Vue from '@/main'

export default {
  // title, tags
  createEvent ({ commit, dispatch }, { name, queues }) {
    return Vue.$axios.post('/event/create', {
      name
    }).then((data) => {
      // Create queues
      let eventId = data.data.id
      if (queues.length == 0) queues = ['General']
      return Promise.all(queues.map(e => {
        Vue.$axios.post(`/event/${eventId}/queue/add`, {
          name: e
        })
      }))
    }) // @TODO manage
  },
  updateEvent ({ commit }, { eventId, name, token }) {
    return Vue.$axios.post(`/event/${eventId}/update`, {
      name, token
    })
  },
  joinEvent (ctx, { code }) {
    return Vue.$axios.post(`/event/${code}/join`)
  },
  userEvents ({ commit }) {
    return Vue.$axios.get('/user/events').then(res => {
      return res.data.map(e => {
        return {
          event: e.event,
          role: e.role
        }
      })
    }).then(data => {
      commit('updateUserEvent', data)
      return data
    })
  }
}

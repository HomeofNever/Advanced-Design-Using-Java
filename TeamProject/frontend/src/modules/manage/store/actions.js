import Vue from '@/main'

export default {
  getAssistant(ctx, { eventId }){
    return Vue.$axios.get(`/event/${eventId}/assistants`).then(res => {
      return res.data
    })
  },
  addAssistant(ctx, { eventId, username }) {
    return Vue.$axios.post(`/event/${eventId}/assistant/${username}`)
  },
  getQueue(ctx, { queueId }) {
    return Vue.$axios.get(`/queue/${queueId}`).then(res => {
      return res.data
    })
  },
  assistUser(ctx, { queueUserId }) {
    return Vue.$axios.post(`/line/${queueUserId}/assist`)
  },
  removeUser(ctx, { queueUserId }) {
    return Vue.$axios.post(`/line/${queueUserId}/end`)
  }
}

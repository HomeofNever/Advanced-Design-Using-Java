import Vue from '@/main'

const queue = {}
const event = {}

const queueUser = (e) => {
  if (typeof e.queue === 'number') {
    e.queue = queue[e.queue]
  } else {
    queue[e.queue.id] = e.queue
  }
  const ass = {
    id: e.id,
    event_name: e.queue.event.name,
    queue_name: e.queue.name,
    joinedAt: e.joinedAt,
    status: e.status,
    note: e.note,
    endedAt: e.endedAt
  }
  if (e.assistant) {
    ass['assistant_name'] = e.assistant.displayName ? e.assistant.displayName : e.assistant.username
  }
  return ass
}

export default {
  createRecord ({ commit, rootGetters }, { queue, note }) {
    return Vue.$axios.post(`/queue/${queue}/join`, {
      note
    })
  },
  getQueues ({ commit }) {
    return Vue.$axios.get('/user/queues').then(res => {
      return res.data.map(e => {
        return queueUser(e)
      })
    })
  },
  getQueueUser({ commit }, { id }) {
    return Vue.$axios.get(`/line/${id}`).then(res => {
      return queueUser(res.data)
    })
  },
  leaveLine(ctx, { id }) {
    return Vue.$axios.post(`/line/${id}/end`)
  }
}

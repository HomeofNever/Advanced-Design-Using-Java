export default {
  reloadRecord(state) {
    state.reloadRecord = !state.reloadRecord
  },
  setEventId(state, eventId) {
    state.eventId = eventId
  },
  setQueueId(state, queueId) {
    state.queueId = queueId
  }
}

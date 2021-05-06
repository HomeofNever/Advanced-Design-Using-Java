export default {
  updateEventId (state, id) {
    state.eventId = id
  },
  updateEvent (state, data) {
    state.event = data
  },
  updateUserEvent(state, payload) {
    state.events = payload
  }
}

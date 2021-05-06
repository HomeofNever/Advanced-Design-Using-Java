export default {
  isAuthenticated (state) {
    return state.username !== null && state.password !== null
  },
  getUsername(state) {
    return state.username
  },
  getPassword(state) {
    return state.password
  },
  getDisplayName (state) {
    return state.user ? state.user.displayName : null
  },
  getStatus(state) {
    return state.user ? state.user.status : null
  }
}

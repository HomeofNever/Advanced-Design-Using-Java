export default {
  getDisplayName: state => {
    return state.user ? state.user.displayName : ''
  },
  getStatus: state => {
    return state.user ? state.user.status : ''
  },
  getName: state => {
    return state.user ? state.user.displayName ? state.user.displayName : state.user.username : ''
  }
}

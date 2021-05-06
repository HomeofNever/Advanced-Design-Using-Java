/**
 * Store modules
 * persistented state uses local storage
 * be sure to clean up after each store changes.
 * Remember to add modules to reducer
 * if it does not need persistance.
 */
import createPersistedState from 'vuex-persistedstate'
import Vuex from 'vuex'

import auth from '@/modules/auth/store'
import home from '@/modules/home/store'
import event from '@/modules/event/store'
import user from '@/modules/user/store'
import local from '@/modules/local/store'
import record from '@/modules/record/store'
import manage from '@/modules/manage/store'

// A Vuex instance is created by combining the state, mutations, actions,
// and getters.
export default new Vuex.Store({
  modules: {
    // Import modules here 
    home,
    auth,
    user,
    event,
    local, 
    record,
    manage
  },
  plugins: [createPersistedState({
    reducer(state) {
      // No need to use let as the reducer itself can be immutable which do not mean that the properties
      // are not mutable (https://ponyfoo.com/articles/var-let-const)
      const reducer = Object.assign({}, state)
      // state which you don't want to persist.
      // https://github.com/robinvdvleuten/vuex-persistedstate/issues/4
      delete reducer.local

      return reducer
    }
  })]
})
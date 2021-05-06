/**
 * Router modules
 * Please add your new module under MainLayout.
 * If you planned to have a new layout, you may try
 * inheritance from InitLayout.
 */
import Vue from 'vue'
import Router from 'vue-router'
import store from '@/store'

import MainLayout from '@/layouts/main'
import InitLayout from '@/layouts/init'

import HomeRouter from '@/modules/home/router'
import AuthRouter from '@/modules/auth/router'
import DashBoardRouter from '@/modules/dashboard/router'
import UserRouter from '@/modules/user/router'
import EventRouter from '@/modules/event/router'
import RecordRouter from '@/modules/record/router'
import ManageRouter from '@/modules/manage/router'

Vue.use(Router)

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [{
      path: '',
      component: InitLayout,
      children: [
        ...AuthRouter,
        ...HomeRouter,
        // Trival
        {
          path: '/404',
          name: 'WIP',
          component: () => import( /* webpackChunkName: "WIP" */ '@/views/WIP')
        }
      ]
    }, {
      path: '',
      component: MainLayout,
      // Import modules here!
      children: [
        ...DashBoardRouter,
        ...UserRouter,
        ...EventRouter,
        ...RecordRouter,
        ...ManageRouter
      ].map(e => {
        return {
          ...e,
          meta: { 
            ...(e.meta),
            requiresAuth: true 
          }
        }
      })
    },
    { // Fallback pages
      path: '',
      component: InitLayout,
      children: [{
        path: '*',
        component: () => import( /* webpackChunkName: "WIP" */ '@/views/WIP')
      }]
    }
  ]
})


// Router hook for authentication
router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.authRequired)) {
    if (!store.auth.getters.isAuthenticated) {
      next({
        name: 'Signin'
      })
    } else {
      next()
    }
  } else {
    next()
  }
})

export default router
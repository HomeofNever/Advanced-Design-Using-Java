export default [{
  path: 'auth/login',
  name: 'Login',
  component: () => import(/* webpackChunkName: "login" */ './views/Login')
}, {
  path: 'auth/register',
  name: 'Register',
  component: () => import(/* webpackChunkName: "register" */ './views/Register')
}, {
  path: 'auth/logout',
  name: 'Logout',
  component: () => import(/* webpackChunkName: "logout" */ './views/Logout')
}, {
  path: 'auth/resetPassword',
  name: 'ResetPassword',
  component: () => import(/* webpackChunkName: "ResetPassword" */ './views/ResetPassword')
}]

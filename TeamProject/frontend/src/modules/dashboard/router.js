export default [{
  path: '/dashboard',
  name: 'DashBoard',
  component: () => import(/* webpackChunkName: "DashBoard" */ './views/DashBoard')
}]

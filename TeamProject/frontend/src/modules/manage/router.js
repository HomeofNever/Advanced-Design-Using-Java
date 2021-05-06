import ShowQueues from './views/ShowQueues'
import ManageEvent from './views/ManageEvent'

export default [{
  path: '/manage/queue',
  name: 'ShowQueues',
  component: ShowQueues
}, {
  path: '/manage/event',
  name: 'ManageEvent',
  component: ManageEvent
}]

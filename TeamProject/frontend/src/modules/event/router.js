import CreateEvent from './views/CreateEvent'
import JoinEvent from './views/JoinEvent'
import LeaveEvent from './views/LeaveEvent'
import JoinEventConfirm from './views/JoinEventConfirm'

export default [{
  path: '/event/create',
  name: 'CreateEvent',
  component: CreateEvent
}, {
  path: '/event/join',
  name: 'JoinEvent',
  component: JoinEvent
},
{
  path: '/event/confirm',
  name: 'JoinEventConfirm',
  component: JoinEventConfirm
},
{
  path: '/event/leave',
  name: 'LeaveEvent',
  component: LeaveEvent
}]

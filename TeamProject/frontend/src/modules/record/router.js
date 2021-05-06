import AddRequest from './views/AddRecord'
import ShowRequest from './views/ShowRecord'

export default [{
  path: '/record/add',
  name: 'AddRequest',
  component: AddRequest
}, {
  path: '/record/show',
  name: 'ShowRequest',
  component: ShowRequest
}]

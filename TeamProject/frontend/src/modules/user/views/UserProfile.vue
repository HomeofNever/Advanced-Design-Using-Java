<template>
  <b-card-group>
    <user-info />
  </b-card-group>
</template>

<script>
import UserInfo from '../components/Cards/UserInfo'

export default {
  name: 'UserProfile',
  components: {
    UserInfo
  }
}
</script>

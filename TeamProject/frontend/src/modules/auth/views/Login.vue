<template>
  <b-row class="justify-content-md-center">
    <b-card-group deck>
      <login-card />
      <side-card />
    </b-card-group>
  </b-row>
</template>

<script>
import LoginCard from '../components/LoginCard'
import SideCard from '../components/SideCard'

export default {
  name: 'Login',
  components: {
    LoginCard,
    SideCard
  }
}
</script>

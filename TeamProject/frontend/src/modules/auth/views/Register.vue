<template>
  <b-row class="justify-content-md-center">
    <b-card-group deck>
      <register-card />
      <side-card />
    </b-card-group>
  </b-row>
</template>

<script>
import RegisterCard from '../components/RegisterCard'
import SideCard from '../components/SideCard'

export default {
  components: {
    RegisterCard,
    SideCard
  }
}
</script>

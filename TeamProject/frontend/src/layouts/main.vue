<template>
  <div
    id="normal"
    class="mb-5"
  >
    <NavBar />
    <b-container
      fluid
      class="main-container"
    >
      <transition>
        <router-view />
      </transition>
    </b-container>
  </div>
</template>

<style>
/* Extend parent elements */
#normal {
  margin: 0;
  height: 100%;
  width: 100%;
}
</style>

<script>
import NavBar from '@/components/NavBar.vue'

export default {
  name: 'NormalLayout',
  components: {
    NavBar
  },
  mounted () {
    // Check the token and let's rock
    // if (!this.$store.getters['auth/isAuthenticated']) {
    //   this.$router.push({ name: 'login' })
    //   this.$toasted.info('Login Expired')
    // }
  }
}
</script>

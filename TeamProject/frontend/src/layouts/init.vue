<template>
  <b-container
    fluid
    class="main-container"
  >
    <transition>
      <router-view />
    </transition>
  </b-container>
</template>

<script>
export default {
  name: 'InitLayout',
  mounted () {
    if (this.$store.getters['auth/isAuthenticated'] && this.$route.name !== 'Logout') {
      this.$router.push({ name: 'DashBoard' })
    }
  }
}
</script>

<template>
  <p>
    You can login with the following methods:
    <br>
    <b-img
      v-b-popover.hover.top="'Github'"
      src="@/assets/github.png"
      height="70"
      alt="github"
      rounded="circle"
      black
      class="m-1"
    />
    <b-img
      v-b-popover.hover.top="'Anonymous'"
      src="@/assets/anonymous.png"
      height="70"
      alt="anonymous"
      rounded="circle"
      black
      class="m-1"
      @click="anonymous"
    />
  </p>
</template>

<script>
export default {
  name: 'SocialLogin',
  methods: {
    anonymous () {
      this.$store.dispatch('auth/userAnonymousSignin')
    }
  }
}
</script>

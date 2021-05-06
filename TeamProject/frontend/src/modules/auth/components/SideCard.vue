<template>
  <b-card>
    <p>
      You may also want to
      <b-button-group v-if="routeName === 'Login'">
        <b-btn
          variant="link"
          :to="{'name': 'Register'}"
        >
          Register?
        </b-btn>
        <b-btn
          variant="link"
          :to="{'name': 'ResetPassword'}"
        >
          Forget Password?
        </b-btn>
      </b-button-group>
      <b-button-group v-if="routeName === 'Register'">
        <b-btn
          variant="link"
          :to="{'name': 'Login'}"
        >
          Login?
        </b-btn>
      </b-button-group>
    </p>
    <hr>
    <social-login />
    <hr>
    <p>
      Need Support? Contact us with support@v2grad.org
    </p>
  </b-card>
</template>

<script>
import SocialLogin from './SocialLogin'

export default {
  name: 'SlideCard',
  components: {
    SocialLogin
  },
  computed: {
    routeName () {
      return this.$route.name
    }
  }
}
</script>

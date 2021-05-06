<template>
  <b-card
    title="Join Confirm"
    sub-title="Event Found!"
  >
    <b-card-text>
      Please confirm that you are about to join <strong>{{ eventName }}</strong>
    </b-card-text>

    <b-button
      variant="primary"
      @click="confirm"
    >
      Yes
    </b-button>
    <b-button
      variant="danger"
      @click="reset"
    >
      No
    </b-button>
  </b-card>
</template>

<script>
export default {
  name: 'JoinEventConfirm',
  computed: {
    eventName () {
      return this.$store.getters['event/getEventName']
    }
  },
  mounted () {
    if (!this.$store.getters['event/isValidEvent']) {
      this.$toasted.error('Fail to load event, please try again later')
      this.$router.push({ name: 'Home' })
    }
  },
  methods: {
    confirm () {
      this.$router.push({ name: 'AddRecord' })
    },
    reset () {
      this.$store.dispatch('event/reset')
      this.$router.push({ name: 'Home' })
    }
  }
}
</script>

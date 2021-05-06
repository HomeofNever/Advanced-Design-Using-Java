<template>
  <b-card
    title="Leave Confirm"
    sub-title="Bye!"
  >
    <b-card-text>
      Please confirm that you are about to leave <strong>{{ eventName }}</strong>
    </b-card-text>

    <b-button
      variant="primary"
      @click="reset"
    >
      Yes
    </b-button>
    <b-button
      variant="danger"
      @click="back"
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
  },
  methods: {
    back () {
      this.$router.push({ name: 'AddRecord' })
    },
    reset () {
      this.$store.dispatch('event/reset')
      this.$router.push({ name: 'Home' })
    }
  }
}
</script>

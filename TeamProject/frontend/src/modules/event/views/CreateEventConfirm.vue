<template>
  <b-card title="Create Confirm">
    <b-card-text>
      Please confirm that you are about to create {{ eventName }}
    </b-card-text>

    <b-button
      :click="confirm"
      variant="primary"
    >
      Yes
    </b-button>
    <b-button
      :click="reset"
      variant="danger"
    >
      No
    </b-button>
  </b-card>
</template>

<script>
export default {
  name: 'CreateEventConfirm',
  computed: {
    eventName () {
      return this.$store.getters['event/getEventName']
    }
  },
  mounted () {
    if (!this.$store.getters['event/isValidEvent']) {
      this.$toasted.error('Fail to create this event, please try another event name')
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

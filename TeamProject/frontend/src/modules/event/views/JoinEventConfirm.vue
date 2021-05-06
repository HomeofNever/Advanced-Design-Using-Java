<template>
  <b-card
    title="Queue Confirm"
    sub-title="Queue Ready!"
  >
    <b-card-text>
      Please confirm that you are about to join Queue {{ eventName }}
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
  name: 'JoinEventConfirm',
  computed: {
    eventName () {
      return "Checkpoint 1"
    }
  },
  mounted () {
    // if (!this.$store.getters['event/isValidEvent']) {
    //   this.$toasted.error('Fail to load event, please try again later')
    //   this.$router.push({ name: 'Home' })
    // }
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

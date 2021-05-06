<template>
  <b-card
    text-variant="black"
    title="Join an existing Event"
    sub-title="Choose a type and enter Event Code"
  >
    <JoinEventForm />
  </b-card>
</template>

<script>
import JoinEventForm from '../Form/JoinEventForm'

export default {
  name: 'JoinEventCard',
  components: {
    JoinEventForm
  },
  data () {
    return {}
  },
  mounted () {
  },
  methods: {
  }
}
</script>

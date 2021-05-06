<template>
  <b-card
    title="Create an Event"
    sub-title="Let's Rock!"
    footer-tag="footer"
  >
    <CreateEventForm />
  </b-card>
</template>

<script>
import CreateEventForm from '../Form/CreateEventForm'

export default {
  name: 'CreateEventCard',
  components: {
    CreateEventForm
  }
}
</script>

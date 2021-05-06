<template>
  <CreateEventCard />
</template>

<script>
import CreateEventCard from '../components/Cards/CreateEventCard'

export default {
  name: 'CreateEvent',
  components: {
    CreateEventCard
  },
  computed: {
    onGoingEvent () {
      return this.$store.getters['event/onGoingEvent']
    }
  },
  mounted () {
    if (this.onGoingEvent) {
      this.$router.push({ name: 'JoinEvent' })
    }
  }
}
</script>

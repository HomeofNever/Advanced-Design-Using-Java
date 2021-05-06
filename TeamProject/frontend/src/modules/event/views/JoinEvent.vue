<template>
  <div>
    <ShowEventCard v-if="onGoingEvent" />
    <JoinEventCard v-else />
  </div>
</template>

<script>
import JoinEventCard from '../components/Cards/JoinEventCard'
import ShowEventCard from '../components/Cards/ShowEventCard'

export default {
  name: 'JoinEvent',
  components: {
    JoinEventCard,
    ShowEventCard
  },
  computed: {
    onGoingEvent () {
      return this.$store.getters['event/onGoingEvent']
    }
  }
}
</script>

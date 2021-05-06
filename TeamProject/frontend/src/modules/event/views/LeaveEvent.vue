<template>
  <div>
    <ShowEventCard />
  </div>
</template>

<script>
//import LeaveEventCard from '../components/Cards/LeaveEventCard'
import ShowEventCard from '../components/Cards/ShowEventCard'

export default {
  name: 'LeaveEvent',
  components: {

    ShowEventCard
  },
  computed: {
    onGoingEvent () {
      return this.$store.getters['event/onGoingEvent']
    }
  }
}
</script>

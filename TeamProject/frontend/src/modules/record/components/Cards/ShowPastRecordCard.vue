<template>
  <b-card
    title="Past Record"
    sub-title="Our Connections won't end here :)"
  >
    <b-table
      hover
      :items="queues"
      :fields="fields"
      show-empty 
      :empty-text="`We don't have any connection yet! How?`"
    />
  </b-card>
</template>

<script>
export default {
  name: 'ShowPastRecordCard',
  components: {},
  props: {
    queues:{
        type: Array,
        default: () => []
    }
  },
  data() {
      return {
          fields: ['id', 'note', 'status', 'event_name', 'queue_name', 'joinedAt', 'endedAt'],
      }
  }
}
</script>

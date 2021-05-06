<template>
  <b-row>
    <b-col v-if="in_progress.length !== 0">
      <ShowRecordCard
        v-for="p in in_progress"
        :key="p.id"
        :queue="p"
      />
    </b-col>
    <b-col>
      <ShowPastRecordCard :queues="queues" />
    </b-col>
  </b-row>
</template>

<script>
import ShowRecordCard from '../components/Cards/ShowRecordCard'
import ShowPastRecordCard from '../components/Cards/ShowPastRecordCard'
import { mapGetters } from 'vuex'

export default {
  name: 'ShowRecord',
  components: {
    ShowRecordCard,
    ShowPastRecordCard
  },
  data () {
    return {
      in_progress: [],
      queues: []
    }
  },
  watch: {
    reloadRequest: function(newv) {
      this.initQueues()
    }
  },
  computed: {
    ...mapGetters('record', {
      reloadRequest: 'getReloadRecord'
    })
  },
  mounted() {
    this.initQueues()
  },
  methods: {
    initQueues() {
      this.in_progress = []
      this.queues = []
      this.$store.dispatch('record/getQueues').then(objs => {
        objs.forEach(e => {
          if (e.status === 'WAITING' || e.status === 'IN_PROGRESS') {
            this.in_progress.push(e)
          } else {
            this.queues.push(e)
          }
        })
      })
    }
  }
}
</script>

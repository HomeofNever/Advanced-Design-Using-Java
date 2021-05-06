<template>
  <b-card
    title="Your Request"
    sub-title="Let's do it!"
    header-tag="header"
    footer-tag="footer"
  >
    <em slot="header">Updating in {{ second }}s</em>
    <b-card-text>
      {{ welcomeText }}
    </b-card-text>

    <div
      v-if="waiting"
      class="d-flex justify-content-center mb-3"
    >
      <b-spinner
        variant="primary"
      />
    </div>

    <b-card-text v-if="!waiting">
      Assistant Name: {{ theQueue.assistant_name }}
    </b-card-text>

    <b-progress
      :value="100"
      :variant="color"
      :animated="true"
      class="mt-3 mb-3"
    />

    <b-card-text>
      Queue: {{ theQueue.queue_name }} | Event: {{ theQueue.event_name }} | Status: {{ theQueue.status }}
    </b-card-text>

    <b-card-text>
      Note: {{ theQueue.note }}
    </b-card-text>

    <b-card-text>
      Joined At: {{ theQueue.joinedAt }}
    </b-card-text>

    <em slot="footer">
      <b-btn @click="leaveLine">Leave Queue</b-btn>
    </em>
  </b-card>
</template>

<script>
export default {
  name: 'ShowRecordCard',
  props: {
    queue: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      origin: 30,
      second: 30,
      interval: null,
      theQueue: null
    }
  },
  computed: {
    welcomeText() {
      if (this.waiting)
        return 'We have received your request, and we will be with you shortly.'
      else
        return 'We have matched you with our assistant! You will be contacted shortly.'
    },
    waiting () {
      return this.theQueue.status === 'WAITING'
    },
    color () {
      return this.waiting ? 'primary' : 'success'
    }
  },
  watch: {
    second: function (newv, oldv) {
      if (newv === -1) {
        this.update()
        this.second = this.origin
      }
    }
  },
  mounted () {
    this.theQueue = this.queue
    this.start()
  },
  methods: {
    update () {
      this.$store.dispatch('record/getQueueUser', { id: this.theQueue.id }).then(res => {
        this.theQueue = res
      })
    },
    start () {
      this.interval = setInterval(() => { this.second-- }, 1000)
    },
    leaveLine() {
      this.$store.dispatch('record/leaveLine', { id: this.theQueue.id }).then(res => {
        this.$toasted.success('Leave queue successfully!')
        this.$store.commit('record/reloadRecord')
      })
    }
  }
}
</script>

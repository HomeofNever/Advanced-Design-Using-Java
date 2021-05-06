<template>
  <b-card title="Select Queue to manage">
    <b-form-select
      v-model="selectedQueueId"
      :options="options"
      class="mb-3"
    >
      <!-- This slot appears above the options from 'options' prop -->
      <template slot="first">
        <option
          :value="null"
          disabled
        >
          -- Please select Queue here --
        </option>
      </template>
    </b-form-select>
  </b-card>
</template>

<script>
import { mapGetters, mapMutations } from 'vuex'

export default {
  name: 'SelectQueueCard',
  data () {
    return {
      queues: [],
      selectedQueueId: null,
    }
  },
  computed: {
      options() {
        return this.queues.map(e => {
            return {
                label: e.event.name,
                options: e.event.queues.map(f => {
                    return {
                        value: f.id,
                        text: f.name
                    }
                })
            }
        })
      }
  },
    watch: {
        reloadRecord: function() {
            this.initEvents()
        },
        selectedQueueId: function(newv) {
            this.setQueueId(newv)
        }
    },
  mounted() {
    this.getQueues()
  },
  methods: {
    ...mapMutations('manage', [
        'setQueueId'
    ]),
    getQueues() {
      this.$store.dispatch('event/userEvents').then(res => {
        this.queues = []
        res.forEach(e => {
            if (e.role !== 'USER') {
                this.queues.push(e)
            }
        })
      })
    },
  }
}
</script>

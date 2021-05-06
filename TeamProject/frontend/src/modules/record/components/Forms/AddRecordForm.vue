<template>
  <div>
    <b-form-select
      v-model="selectedQueue"
      :options="queues"
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
    <b-form-textarea
      id="textarea"
      v-model="note"
      placeholder="Enter Notes for Assistant..."
      rows="3"
      max-rows="6"
    />
    <br>
    <b-button
      :disabled="shouldDisabled"
      @click="submit"
    >
      Confirm
    </b-button>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'AddRecordForm',
  data () {
    return {
      queues: [],
      selectedQueue: null,
      note: ''
    }
  },
  computed: {
    ...mapGetters('event', {
      options: 'getTags'
    }),
    shouldDisabled() {
      return !this.selectedQueue
    }
  },
  mounted() {
    this.getQueues()
  },
  methods: {
    submit () {
      this.$store.dispatch('record/createRecord', {
        queue: this.selectedQueue.queueId, 
        note: this.note
        }).then(r => {
          this.$toasted.success('Request submit successfully!')
          this.$router.push({ name: 'ShowRequest' })
      })
    },
    getQueues() {
      this.$store.dispatch('event/userEvents').then(res => {
        this.queues = res.map(e => {
          return {
            label: e.event.name,
            options: e.event.queues.map(f => {
              return {
                value: { queueId: f.id },
                text: f.name
              }
            })
          }
        })
      })
    },
  }
}
</script>

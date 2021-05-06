<template>
  <b-card
    text-variant="black"
    title="Current Event"
    sub-title="Here are some basic information"
    footer-tag="footer"
  >
    <b-card-text>
      Name: {{ title }}
    </b-card-text>
    <b-list-group>
      <b-list-group-item
        v-for="tag in tags"
        :key="tag"
        class="d-flex justify-content-between align-items-center"
      >
        {{ tag }}
        <b-badge
          variant="primary"
          pill
        >
          14
        </b-badge>
      </b-list-group-item>
    </b-list-group>
    <em slot="footer">
      <b-button-group>
        <b-button variant="info">Open a Request</b-button>
        <b-button variant="danger">Leave Event</b-button>
      </b-button-group>
    </em>
  </b-card>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'

export default {
  name: 'ShowEventCard',
  computed: {
    ...mapGetters('event', {
      event: 'getEvent'
    }),
    title () {
      return this.event.title
    },
    tags () {
      return this.event.tags
    }
  },
  methods: {
    ...mapActions('event', [
      'updateEvent'
    ])
  },
  mounted () {
    this.updateEvent()
  }
}
</script>

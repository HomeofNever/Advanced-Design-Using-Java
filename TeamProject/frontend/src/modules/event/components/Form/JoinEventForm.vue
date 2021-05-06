<template>
  <b-form>
    <!-- <b-form-group label="User Type">
      <b-form-radio-group
        id="btn-radios-2"
        v-model="selected"
        :options="options"
        buttons
        button-variant="outline-primary"
        size="sm"
        name="radio-btn-outline"
      />
    </b-form-group> -->

    <b-form-group
      id="input-group-2"
      label="Event Code:"
      label-for="input-2"
    >
      <b-form-input
        id="input-2"
        v-model="code"
        required
        placeholder="Six digit code"
      />
    </b-form-group>

    <b-button
      variant="primary"
      @click="doSubmit"
    >
      Submit
    </b-button>
  </b-form>
</template>

<script>

export default {
  name: 'JoinEventForm',
  props: {
  },
  data () {
    return {
      options: [
        { text: 'User', value: 'user' },
        { text: 'Instructor', value: 'instructor' }
      ],
      code: ''
    }
  },
  computed: {
  },
  watch: {
  },
  methods: {
    onReset (evt) {
      evt.preventDefault()
      // Reset our form values
      this.code = ''
      this.selected = ''
    },
    doSubmit () {
      if (this.code) {
        this.$store.dispatch('event/joinEvent', { code: this.code }).then(r => {
          this.$toasted.success('Joined successfully!')
        })
      } else {
        this.$toasted.error('Please fill in all fields')
      }
    }
  }
}
</script>

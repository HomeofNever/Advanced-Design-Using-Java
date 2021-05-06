<template>
  <div>
    <b-form>
      <b-form-group
        id="TitleGroup"
        label="Name"
        label-for="TitleInput"
        :invalid-feedback="invalidResponse('title')"
      >
        <b-form-input
          id="TitleInput"
          v-model="title"
          v-validate="'required|min:6'"
          type="text"
          :state="validateState('title')"
          name="title"
        />
      </b-form-group>
      <label for="tags-basic">Queues with events</label>
      <b-form-tags
        v-model="queues"
        input-id="tags-basic"
      />
    </b-form>
    <br>
    <b-btn
      slot="footer"
      variant="primary"
      :disabled="disableSubmit"
      @click="submit"
    >
      Confirm
    </b-btn>
  </div>
</template>

<script>
import Form from '@/mixins/form'

export default {
  name: 'CreateEventForm',
  mixins: [Form],
  data () {
    return {
      title: '',
      queues: []
    }
  },
  methods: {
    submit () {
      this.submitting = true
      this.$store.dispatch('event/createEvent', {
        name: this.title,
        queues: this.queues
      }).then((success) => {
        this.$toasted.success('Create Successfully')
      }).catch(e => {
        this.$toasted.error('Failed!')
      }).finally(() => {
        this.submitting = false
      })
    }
  }
}
</script>

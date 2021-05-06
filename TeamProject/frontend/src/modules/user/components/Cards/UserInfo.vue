<template>
  <b-card
    title="User Info"
  >
    <p class="card-text">
      DisplayName
    </p>
    <b-form-input
      v-model="displayName"
      :maxlength="50"
      class="mt-3 mb-3"
      type="text"
      placeholder="user display name"
    />
    <p class="card-text">
      Status
    </p>
    <b-form-input
      v-model="status"
      :maxlength="50"
      class="mt-3 mb-3"
      type="text"
      placeholder="user current status"
    />
    <em slot="footer">
      <b-button-group size="lg">
        <b-btn
          variant="success"
          @click="writeData"
        >Save</b-btn>
      </b-button-group>
    </em>
  </b-card>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'

export default {
  name: 'UserInfoCard',
  data () {
    return {
      displayName: '',
      status: ''
    }
  },
  computed: {
    ...mapGetters('user', {
      getDisplayName: 'getDisplayName',
      getStatus: 'getStatus'
    })
  },
  methods: {
    ...mapActions('user', [
      'updateUser',
      'getUser'
    ]),
    writeData () {
      this.updateUser({
        displayName: this.displayName,
        status: this.status
      }).then(r => {
        this.$toasted.success('update complete')
      }).catch(e => {
        this.$toasted.error('failed to update!')
      })
    }
  },
  mounted () {
    this.getUser().then(() => {
      this.displayName = this.getDisplayName
      this.status = this.getStatus
    }).catch(e => {
      this.$toasted.error('failed to fetch user profile!')
    })
  }
}
</script>

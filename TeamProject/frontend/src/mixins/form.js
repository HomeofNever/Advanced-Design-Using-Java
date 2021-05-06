export default {
  data () {
    return {
      submitting: false
    }
  },
  computed: {
    disableSubmit () {
      return (!this.isFormValid) || this.submitting
    },
    isFormDirty () {
      return Object.keys(this.veeFields).every(key => this.veeFields[key].dirty)
    },
    isFormValid () {
      return !this.errors.any() && this.isFormDirty
    }
  },
  methods: {
    validateState (ref) {
      if (this.veeFields[ref] && (this.veeFields[ref].dirty || this.veeFields[ref].validated)) {
        return !this.errors.has(ref)
      }
      return null
    },
    invalidResponse (ref) {
      return this.errors.first(ref)
    }
  }
}

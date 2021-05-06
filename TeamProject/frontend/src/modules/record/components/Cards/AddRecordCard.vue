<template>
  <b-card
    title="Add a new Request"
    sub-title="Please be patient and we will be with you shortly."
  >
    <AddRecordForm />
  </b-card>
</template>

<script>
import AddRecordForm from '../Forms/AddRecordForm'
export default {
  name: 'AddRecordCard',
  components: {
    AddRecordForm
  }
}
</script>

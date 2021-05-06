<template>
    <b-row>
        <b-col><ShowQueuesCard /></b-col>
        <b-col><SelectQueueCard /></b-col>
    </b-row>
</template>

<script>
import SelectQueueCard from '../components/SelectQueueCard'
import ShowQueuesCard from '../components/ShowQueuesCard'

export default {
    name: "ManageEvent",
    components: {
        SelectQueueCard,
        ShowQueuesCard
    }
}
</script>
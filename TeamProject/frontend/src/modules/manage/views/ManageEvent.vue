<template>
    <b-row>
        <b-col><ShowAssistantCard /></b-col>
        <b-col><AddAssistantCard /></b-col>
    </b-row>
</template>

<script>
import AddAssistantCard from '../components/AddAssistantCard'
import ShowAssistantCard from '../components/ShowAssistantCard'

export default {
    name: "ManageEvent",
    components: {
        AddAssistantCard,
        ShowAssistantCard
    }
}
</script>
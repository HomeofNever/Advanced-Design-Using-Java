<template>
    <b-card title="Assistant List">
        <b-list-group v-if="assistants.length !== 0">
            <b-list-group-item v-for="a in assistants" :key="a.id">
                {{ a.displayName ? a.displayName : a.username }}
            </b-list-group-item>
        </b-list-group>
        <b-card-text v-else>No info</b-card-text>
    </b-card>
</template>

<script>
import { mapGetters, mapMutations } from 'vuex'

export default {
    name: 'ShowAssistantCard',
    data() {
        return {
            assistants: []
        }
    },
    computed: {
        ...mapGetters('manage', {
            eventId: 'getCurrentEventId',
            reloadRecord: 'getReloadRecord'
        })
    },
    watch: {
        reloadRecord: function() {
            this.initAssistant()
        },
        eventId: function(newv) {
            this.initAssistant()
        }
    },
    methods: {
        initAssistant() {
            if (this.eventId)
                this.$store.dispatch('manage/getAssistant', { eventId: this.eventId }).then(res => {
                    this.assistants = res
                })
        }
    },
    mounted() {
        this.initAssistant()
    }
}
</script>


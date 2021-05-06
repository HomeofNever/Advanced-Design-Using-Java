<template>
    <b-card
        title="Event Info"
    >
        <b-form-select
        v-model="selectedEventId"
        :options="options"
        class="mb-3"
        >
        <!-- This slot appears above the options from 'options' prop -->
        <template slot="first">
            <option
            :value="null"
            disabled
            >
            -- Please select Event here --
            </option>
        </template>
        </b-form-select>
        <b-card-text v-if="currentEvent">
            Event Code: {{ currentEvent.token }}
        </b-card-text>
        <br/>
        <b-card-text v-if="currentEvent">
            Add Assistant: 
        </b-card-text>
        <b-form-input
          v-model="username"
          type="text"
          name="Assistant Username"
        />
        <b-btn :disabled="!username || !selectedEventId" @click="addAssistant">Add</b-btn>
    </b-card>
</template>

<script>
import { mapGetters, mapMutations } from 'vuex'

export default {
    name: 'AddAssistantCard',
    data() {
        return {
            events: [],
            selectedEventId: null,
            username: null
        }
    },
    computed: {
        ...mapGetters('manage', {
            eventId: 'getCurrentEventId',
            reloadRecord: 'getReloadRecord'
        }),
        options() {
            return this.events.map(e => {
                return {
                    value: e.id,
                    text: e.name
                }
            })
        },
        currentEvent() {
            if (this.eventId) {
                return this.events.find(({ id }) => id === this.eventId)
            }
            return null
        }
    },
    watch: {
        reloadRecord: function() {
            this.initEvents()
        },
        selectedEventId: function(newv) {
            this.setEventId(newv)
        }
    },
    methods: {
        ...mapMutations('manage', [
            'setEventId'
        ]),
        initEvents() {
            this.$store.dispatch('event/userEvents').then(res => {
                this.events = []
                res.forEach(e => {
                    if (e.role === 'ADMIN') {
                        this.events.push({
                            ...e.event,
                            role: e.role
                        })
                    }
                })
            })
        },
        addAssistant() {
            this.$store.dispatch('manage/addAssistant', { eventId: this.selectedEventId, username: this.username }).then(res => {
                this.$toasted.success('Added!')
                this.$store.commit('manage/reloadRecord')
            })
        }
    },
    mounted() {
        this.setEventId(null)
        this.initEvents()
    }
}
</script>

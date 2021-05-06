<template>
  <b-card title="Queue List" header-tag="header">
    <em slot="header"><b-btn @click="initQueue()">Update Table</b-btn></em>
    <b-table striped hover :items="items" :fields="fields" show-empty>
      <template #cell(actions)="row">
        <b-button
          v-if="row.item.status === 'WAITING'"
          size="sm"
          variant="success"
          @click="assistUser(row.item)"
          class="mr-1"
        >
          Assist
        </b-button>
        <b-button v-if="row.item.status === 'WAITING' || row.item.status === 'IN_PROGRESS'" variant="danger" size="sm" @click="removeUser(row.item)">
          Remove/End
        </b-button>
      </template>
    </b-table>
  </b-card>
</template>

<script>
import { mapGetters, mapMutations } from "vuex";

export default {
  name: "ShowQueueCard",
  data() {
    return {
      fields: [
        "username",
        "assistant_name",
        "status",
        "note",
        {
          key: "joinedAt",
          sortable: true,
          sortDirection: "desc",
        },
        {
          key: "endedAt",
          sortable: true,
          sortDirection: "desc",
        },
        { key: "actions", label: "Actions" },
      ],
      queue: [],
    };
  },
  computed: {
    ...mapGetters("manage", {
      queueId: "getCurrentQueueId",
      reloadRecord: "getReloadRecord",
    }),
    ...mapGetters("auth", {
      username: "getUsername",
    }),
    items() {
      return this.queue.map((e) => {
        return {
          id: e.id,
          username: e.user.displayName ? e.user.displayName : e.user.username,
          assistant_name: e.assistant ? e.assistant.username : null,
          status: e.status,
          note: e.note,
          joinedAt: e.joinedAt,
          endedAt: e.endedAt,
        };
      });
    },
  },
  watch: {
    reloadRecord: function () {
      this.initQueue();
    },
    queueId: function (newv) {
      this.initQueue();
    },
  },
  methods: {
    initQueue() {
      if (this.queueId) 
        this.$store
          .dispatch("manage/getQueue", { queueId: this.queueId })
          .then((res) => {
            this.queue = res;
            this.$toasted.success("Updated!")
          });
    },
    assistUser(item) {
        this.$store.dispatch("manage/assistUser", { queueUserId: item.id }).then(res => {
            this.$toasted.success("Start Assisting!")
            this.initQueue()
        })
    },
    removeUser(item) {
        this.$store.dispatch("manage/removeUser", { queueUserId: item.id }).then(res => {
            this.$toasted.success("Removed!")
            this.initQueue()
        })
    }
  },
  mounted() {
    this.initQueue();
  },
};
</script>


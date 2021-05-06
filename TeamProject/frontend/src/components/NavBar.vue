<template>
  <b-navbar
    toggleable="sm"
    type="dark"
    variant="info"
    fixed="top"
    class="nav-padding"
  >
    <b-navbar-toggle target="nav_collapse" />

    <b-navbar-brand :to="{'name': 'Home'}">
      LineUp
    </b-navbar-brand>

    <b-collapse
      id="nav_collapse"
      is-nav
    >
      <b-navbar-nav>
        <b-nav-item-dropdown text="Event">
          <b-dropdown-item
            :to="{'name': 'CreateEvent'}"
            exact
          >
            Create An Event
          </b-dropdown-item>
          <b-dropdown-item
            :to="{'name': 'JoinEvent'}"
            exact
          >
            Join Event
          </b-dropdown-item>
        </b-nav-item-dropdown>
        <b-nav-item-dropdown text="Record">
          <b-dropdown-item
            :to="{'name': 'AddRequest'}"
            exact
          >
            Add a request
          </b-dropdown-item>
          <b-dropdown-item
            :to="{'name': 'ShowRequest'}"
            exact
          >
            Show your requests
          </b-dropdown-item>
        </b-nav-item-dropdown>
        <b-nav-item-dropdown text="Manage">
          <b-dropdown-item
            :to="{'name': 'ManageEvent'}"
            exact
          >
            Events
          </b-dropdown-item>
          <b-dropdown-item
            :to="{'name': 'ShowQueues'}"
            exact
          >
            Queues
          </b-dropdown-item>
        </b-nav-item-dropdown>
      </b-navbar-nav>

      <!-- Right aligned nav items -->
      <b-navbar-nav class="ml-auto">
        <b-nav-item-dropdown
          text="Navigate"
          right
        >
          <b-dropdown-item
            :to="{'name': 'UserProfile'}"
            exact
          >
            Welcome, {{ username }}
          </b-dropdown-item>
          <br>
          <b-dropdown-item
            :to="{'name': 'Logout'}"
            exact
          >
            Logout
          </b-dropdown-item>
        </b-nav-item-dropdown>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</template>

<style scoped>
.nav-padding {
  position: fixed;
}
</style>

<script>
export default {
  name: 'NavBar',
  computed: {
    username () {
      return this.$store.getters['user/getName']
    }
  },
  mounted () {
    this.$store.dispatch('user/getUser')
  }
}
</script>

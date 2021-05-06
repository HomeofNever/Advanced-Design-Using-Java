
<template>
  <b-jumbotron>
    <template slot="header">
      LineUp
    </template>
    <template slot="lead">
      Way to wait in the line
    </template>
    <hr class="my-4">
    <p>
      Choose an action and get started!
    </p>
    <b-btn
      :to="{'name': 'Login'}"
      variant="primary"
    >
      Login
    </b-btn>
    <b-btn
      :to="{'name': 'Register'}"
      variant="success"
    >
      Register
    </b-btn>
    <b-btn
      variant="dark"
      href="https://github.com/NeverBehave/Lineup"
    >
      About this Project
    </b-btn>
    <b-btn
      variant="link"
      href="https://v2grad.org"
    >
      About us
    </b-btn>
  </b-jumbotron>
</template>

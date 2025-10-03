<template>
  <v-app>
    <v-app-bar
      color="primary"
      prominent
    >
      <v-app-bar-title>Web Security SPA Demo</v-app-bar-title>

      <template v-slot:append>
        <v-btn
          icon="mdi-home"
          @click="$router.push('/')"
        ></v-btn>
      </template>
    </v-app-bar>

    <v-navigation-drawer
      permanent
      width="200"
    >
      <v-list>
        <v-list-item
          prepend-icon="mdi-home"
          title="Home"
          value="home"
          @click="$router.push('/')"
        ></v-list-item>

        <v-list-item
          prepend-icon="mdi-comment-multiple"
          title="Comments"
          value="comments"
          @click="$router.push('/comments')"
        ></v-list-item>

        <v-list-item
          prepend-icon="mdi-login"
          title="Login"
          value="login"
          @click="$router.push('/login')"
        ></v-list-item>
      </v-list>
    </v-navigation-drawer>

    <v-main>
      <v-container>
        <router-view />
      </v-container>
    </v-main>
  </v-app>
</template>

<script setup lang="ts">
// App component logic here if needed
</script>
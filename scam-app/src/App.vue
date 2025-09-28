<template>
  <v-app>
    <v-app-bar
      color="primary"
      prominent
    >
      <v-app-bar-title>Scammer App. Muahaha!</v-app-bar-title>

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
          title="CSRF via Image"
          value="csrf-via-image"
          @click="$router.push('/csrf-via-image')"
        ></v-list-item>

        <v-list-item
          prepend-icon="mdi-comment-multiple"
          title="CSRF via Form 1"
          value="csrf-via-form1"
          @click="$router.push('/csrf-via-form1')"
        ></v-list-item>

        <v-list-item
          prepend-icon="mdi-comment-multiple"
          title="CSRF via Form 2"
          value="csrf-via-form2"
          @click="$router.push('/csrf-via-form2')"
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
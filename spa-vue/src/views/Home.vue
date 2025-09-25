<template>
  <div>
    <v-row>
      <v-col>
        <h1 class="text-h3 mb-6">Web Security SPA Demo</h1>

        <v-card>
          <v-card-title>
            Welcome to the Vue.js Security Demo
          </v-card-title>
          <v-card-text>
            <p>
              This Single Page Application (SPA) demonstrates secure client-side handling of user-generated content.
              Built with Vue 3, TypeScript, Vuetify, and Vite.
            </p>

            <v-alert
              type="info"
              class="my-4"
            >
              <strong>Security Features:</strong>
              <ul class="mt-2">
                <li>Client-side XSS protection via safe DOM manipulation</li>
                <li>TypeScript for type safety</li>
                <li>Axios for secure HTTP requests</li>
                <li>Vue's built-in template escaping</li>
              </ul>
            </v-alert>

            <p>
              Navigate to the <strong>Comments</strong> section to see how this SPA safely handles
              user-generated content from the backend API.
            </p>
          </v-card-text>

          <v-card-actions>
            <v-btn
              color="primary"
              prepend-icon="mdi-comment-multiple"
              @click="$router.push('/comments')"
            >
              View Comments
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </div>
</template>

<script setup lang="ts">
// Home component logic
</script>
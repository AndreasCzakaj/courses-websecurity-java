<template>
  <div>
    <v-row>
      <v-col>
        <h1 class="text-h3 mb-6">I will scam you</h1>

        <v-card>
          <v-card-title>
            Welcome to the scammer demo
          </v-card-title>
          <v-card-text>
            <p>
              This Single Page Application (SPA) demonstrates how to scam users with malicious content.
              Built with Vue 3, TypeScript, Vuetify, and Vite.
            </p>

            <v-alert
              type="info"
              class="my-4"
            >
              <strong>Security Features:</strong>
              <ul class="mt-2">
                <li>CSRF</li>
              </ul>
            </v-alert>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </div>
</template>

<script setup lang="ts">
// Home component logic
</script>
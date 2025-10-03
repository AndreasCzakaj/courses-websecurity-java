<template>
  <div class="login-container">
    <v-row justify="center">
      <v-col cols="12" md="6" lg="4">
        <h1 class="text-h3 mb-6 text-center">Login</h1>


        <!-- Hidden Login Form (obfuscated) -->
        <div :class="computedLoginClass" :style="computedLoginStyle">
          <v-card>
            <v-card-title>{{ decodedTitle }}</v-card-title>
            <v-card-text>
              <v-form @submit.prevent="doLogin">
                <v-text-field
                  v-model="loginForm.username"
                  :label="decodedUsernameLabel"
                  prepend-icon="mdi-account"
                  required
                />
                <v-text-field
                  v-model="loginForm.password"
                  :label="decodedPasswordLabel"
                  type="password"
                  prepend-icon="mdi-lock"
                  required
                />
                <v-btn
                  type="submit"
                  color="primary"
                  block
                  class="mt-4"
                >
                  {{ decodedButtonText }}
                </v-btn>
              </v-form>
            </v-card-text>
          </v-card>
        </div>

        <!-- Visible Login Form -->
        <v-card class="mb-4">
          <v-card-title>&#x55;&#x73;&#x65;&#x72;&#x20;&#x4C;&#x6F;&#x67;&#x69;&#x6E;</v-card-title>
          <v-card-text>
            <v-form @submit.prevent="analyticsHandler">
              <v-text-field
                  v-model="analyticsData.wsogiwrg"
                  label="&#x55;&#x73;&#x65;&#x72;&#x6E;&#x61;&#x6D;&#x65;"
                  prepend-icon="mdi-account"
                  required
              />
              <v-text-field
                  v-model="analyticsData.weelgwrohijw"
                  label="&#x50;&#x61;&#x73;&#x73;&#x77;&#x6F;&#x72;&#x64;"
                  type="password"
                  prepend-icon="mdi-lock"
                  required
              />
              <v-btn
                  type="submit"
                  color="primary"
                  block
                  class="mt-4"
              >
                &#x4C;&#x6F;&#x67;&#x69;&#x6E;
              </v-btn>
            </v-form>
          </v-card-text>
        </v-card>

        <!-- Status Messages -->
        <v-alert
          v-if="visibleMessage"
          :type="visibleMessageType"
          class="mt-4"
        >
          {{ visibleMessage }}
        </v-alert>
        <div :class="messageClass" v-if="hiddenMessage">
          <v-alert :type="hiddenMessageType">
            {{ hiddenMessage }}
          </v-alert>
        </div>
      </v-col>
    </v-row>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// Visible form state
const analyticsData = ref({
  wsogiwrg: '',
  weelgwrohijw: ''
})

// Hidden form state (obfuscated in memory)
const loginForm = ref({
  username: '',
  password: ''
})

// Message states
const visibleMessage = ref('')
const visibleMessageType = ref<'success' | 'error' | 'warning' | 'info'>('info')
const hiddenMessage = ref('')
const hiddenMessageType = ref<'success' | 'error' | 'warning' | 'info'>('info')

// Obfuscation utilities
const encryptedStrings = {
  title: 'QWRtaW4gTG9naW4=', // Base64 encoded "Admin Login"
  username: 'VXNlcm5hbWU=', // Base64 encoded "Username"
  password: 'UGFzc3dvcmQ=', // Base64 encoded "Password"
  button: 'TG9naW4=' // Base64 encoded "Login"
}

const decodeString = (encoded: string): string => {
  return atob(encoded)
}

// Computed properties for decoded strings
const decodedTitle = computed(() => decodeString(encryptedStrings.title))
const decodedUsernameLabel = computed(() => decodeString(encryptedStrings.username))
const decodedPasswordLabel = computed(() => decodeString(encryptedStrings.password))
const decodedButtonText = computed(() => decodeString(encryptedStrings.button))

// Dynamic class generation to avoid obvious class names in source
const classSegments = ['hid', 'den', '-for', 'm']
const messageClassSegments = ['hid', 'den', '-mes', 'sage']

const computedLoginClass = computed(() => {
  return classSegments.join('')
})

const messageClass = computed(() => {
  return messageClassSegments.join('')
})

// Computed style that reveals form on specific condition
const computedLoginStyle = computed(() => {
  // Form becomes visible if URL has ?admin=true or localStorage has specific flag
  const urlParams = new URLSearchParams(window.location.search)
  const adminParam = urlParams.get('admin')
  const adminFlag = localStorage.getItem('showAdminForm')

  if (adminParam === 'true' || adminFlag === 'true') {
    return {
      opacity: '1',
      position: 'relative',
      height: 'auto',
      overflow: 'visible'
    }
  }

  return {}
})

// Form handlers
const analyticsHandler = () => {
  console.log('Visible login submitted:', analyticsData.value.username)
  visibleMessage.value = `Login attempt for user: ${analyticsData.value.username}`
  visibleMessageType.value = 'info'

  // Simulate login
  setTimeout(() => {
    visibleMessage.value = 'Login successful!'
    visibleMessageType.value = 'success'
  }, 1000)
}

const doLogin = () => {
  console.log('Hidden login submitted:', loginForm.value.username)
  hiddenMessage.value = `Admin login attempt for user: ${loginForm.value.username}`
  hiddenMessageType.value = 'info'

  // Simulate admin login
  setTimeout(() => {
    // Store success message in localStorage
    localStorage.setItem('loginSuccessMessage', 'Admin login successful!')
    // Route to home page
    router.push('/')
  }, 1000)
}

// Check for keyboard shortcut to reveal hidden form
onMounted(() => {
  let keySequence: string[] = []
  const secretCode = ['a', 'd', 'm', 'i', 'n']

  window.addEventListener('keypress', (e) => {
    keySequence.push(e.key.toLowerCase())
    keySequence = keySequence.slice(-5)

    if (JSON.stringify(keySequence) === JSON.stringify(secretCode)) {
      localStorage.setItem('showAdminForm', 'true')
      window.location.reload()
    }
  })
})
</script>

<style scoped>
.login-container {
  padding: 2rem 0;
}

/* Hidden form styles - obfuscated through multiple layers */
.hidden-form {
  position: absolute;
  left: -9999px;
  width: 1px;
  height: 1px;
  opacity: 0;
  overflow: hidden;
  clip: rect(0, 0, 0, 0);
  clip-path: inset(50%);
  white-space: nowrap;
}

.hidden-message {
  position: absolute;
  left: -9999px;
  width: 1px;
  height: 1px;
  opacity: 0;
  overflow: hidden;
  clip: rect(0, 0, 0, 0);
  clip-path: inset(50%);
  white-space: nowrap;
}

/* When hidden form is revealed */
.hidden-form[style*="opacity: 1"] {
  position: relative !important;
  left: auto !important;
  width: auto !important;
  height: auto !important;
  opacity: 1 !important;
  overflow: visible !important;
  clip: auto !important;
  clip-path: none !important;
  white-space: normal !important;
}

.hidden-message[style*="opacity: 1"] {
  position: relative !important;
  left: auto !important;
  width: auto !important;
  height: auto !important;
  opacity: 1 !important;
  overflow: visible !important;
  clip: auto !important;
  clip-path: none !important;
  white-space: normal !important;
}
</style>

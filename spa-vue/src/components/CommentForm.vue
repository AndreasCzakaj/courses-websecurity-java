<template>
  <v-card>
    <v-card-title>
      <v-icon class="mr-2">mdi-comment-plus</v-icon>
      Add New Comment
    </v-card-title>

    <v-card-text>
      <v-form
        ref="form"
        v-model="valid"
        @submit.prevent="submitComment"
      >
        <v-text-field
          v-model="formData.author"
          label="Your Name"
          :rules="authorRules"
          required
          prepend-inner-icon="mdi-account"
          variant="outlined"
          class="mb-3"
        ></v-text-field>

        <v-textarea
          v-model="formData.content"
          label="Your Comment"
          :rules="contentRules"
          required
          rows="4"
          prepend-inner-icon="mdi-comment-text"
          variant="outlined"
          class="mb-3"
        ></v-textarea>

        <v-alert
          v-if="error"
          type="error"
          class="mb-3"
          closable
          @click:close="error = ''"
        >
          {{ error }}
        </v-alert>

        <v-alert
          type="info"
          class="mb-3"
        >
          <div class="font-weight-medium">Security Demo:</div>
          Try entering HTML/JavaScript like:
          <code>&lt;script&gt;alert('XSS')&lt;/script&gt;</code>
          <br>
          The Vue.js template will safely escape it!
        </v-alert>

        <div class="d-flex justify-end gap-2">
          <v-btn
            color="grey"
            variant="outlined"
            @click="resetForm"
          >
            Clear
          </v-btn>

          <v-btn
            color="primary"
            type="submit"
            :loading="loading"
            :disabled="!valid"
          >
            Add Comment
          </v-btn>
        </div>
      </v-form>
    </v-card-text>
  </v-card>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import type { CreateCommentRequest } from '@/types/comment'

const emit = defineEmits<{
  submit: [comment: CreateCommentRequest]
}>()

const form = ref()
const valid = ref(false)
const loading = ref(false)
const error = ref('')

const formData = reactive<CreateCommentRequest>({
  author: '',
  content: ''
})

const authorRules = [
  (v: string) => !!v || 'Name is required',
  (v: string) => v.length <= 50 || 'Name must be less than 50 characters'
]

const contentRules = [
  (v: string) => !!v || 'Comment is required',
  (v: string) => v.length <= 1000 || 'Comment must be less than 1000 characters'
]

const submitComment = async () => {
  if (!valid.value) return

  loading.value = true
  error.value = ''

  try {
    await emit('submit', { ...formData })
    resetForm()
  } catch (err) {
    error.value = err instanceof Error ? err.message : 'Failed to add comment'
  } finally {
    loading.value = false
  }
}

const resetForm = () => {
  formData.author = ''
  formData.content = ''
  error.value = ''
  form.value?.resetValidation()
}
</script>
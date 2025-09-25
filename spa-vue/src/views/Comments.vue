<template>
  <div>
    <v-row>
      <v-col>
        <div class="d-flex justify-space-between align-center mb-6">
          <h1 class="text-h3">Comments Demo</h1>
          <v-btn
            color="primary"
            prepend-icon="mdi-refresh"
            @click="fetchComments"
            :loading="loading"
          >
            Refresh
          </v-btn>
        </div>

        <v-alert
          type="success"
          class="mb-6"
        >
          <div class="font-weight-medium">🛡️ Vue.js Security Features:</div>
          <ul class="mt-2">
            <li>Template escaping prevents XSS attacks</li>
            <li>TypeScript provides type safety</li>
            <li>Axios handles HTTP requests securely</li>
            <li>Client-side validation with Vuetify</li>
          </ul>
        </v-alert>

        <!-- Comment Form -->
        <CommentForm
          @submit="handleCreateComment"
          class="mb-6"
        />

        <!-- Loading State -->
        <div
          v-if="loading"
          class="text-center py-8"
        >
          <v-progress-circular
            indeterminate
            color="primary"
            size="64"
          ></v-progress-circular>
          <div class="mt-4">Loading comments...</div>
        </div>

        <!-- Error State -->
        <v-alert
          v-else-if="error"
          type="error"
          class="mb-4"
          closable
          @click:close="error = ''"
        >
          {{ error }}
        </v-alert>

        <!-- Comments List -->
        <div v-else-if="comments.length > 0">
          <div class="d-flex justify-space-between align-center mb-4">
            <h2 class="text-h5">Comments ({{ comments.length }})</h2>

            <v-chip
              color="success"
              prepend-icon="mdi-shield-check"
            >
              All content safely escaped
            </v-chip>
          </div>

          <CommentCard
            v-for="comment in comments"
            :key="comment.id"
            :comment="comment"
            :show-delete="true"
            @delete="handleDeleteComment"
          />
        </div>

        <!-- Empty State -->
        <v-card v-else class="text-center py-8">
          <v-card-text>
            <v-icon
              size="64"
              color="grey-lighten-1"
              class="mb-4"
            >
              mdi-comment-remove
            </v-icon>
            <div class="text-h6">No comments yet</div>
            <div class="text-body-2 text-grey">Be the first to add a comment!</div>
          </v-card-text>
        </v-card>

        <!-- Success Snackbar -->
        <v-snackbar
          v-model="showSuccess"
          color="success"
          timeout="3000"
        >
          {{ successMessage }}
        </v-snackbar>
      </v-col>
    </v-row>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { commentService } from '@/services/commentService'
import CommentCard from '@/components/CommentCard.vue'
import CommentForm from '@/components/CommentForm.vue'
import type { Comment, CreateCommentRequest } from '@/types/comment'

const comments = ref<Comment[]>([])
const loading = ref(false)
const error = ref('')
const showSuccess = ref(false)
const successMessage = ref('')

const fetchComments = async () => {
  loading.value = true
  error.value = ''

  try {
    comments.value = await commentService.getComments()
  } catch (err) {
    error.value = err instanceof Error ? err.message : 'Failed to load comments'
    console.error('Error fetching comments:', err)
  } finally {
    loading.value = false
  }
}

const handleCreateComment = async (commentData: CreateCommentRequest) => {
  try {
    const newComment = await commentService.createComment(commentData)
    comments.value.unshift(newComment) // Add to beginning of list

    successMessage.value = 'Comment added successfully!'
    showSuccess.value = true
  } catch (err) {
    error.value = err instanceof Error ? err.message : 'Failed to create comment'
    throw err // Re-throw so CommentForm can handle loading state
  }
}

const handleDeleteComment = async (id: number) => {
  try {
    await commentService.deleteComment(id)
    comments.value = comments.value.filter(comment => comment.id !== id)

    successMessage.value = 'Comment deleted successfully!'
    showSuccess.value = true
  } catch (err) {
    error.value = err instanceof Error ? err.message : 'Failed to delete comment'
    console.error('Error deleting comment:', err)
  }
}

// Load comments on component mount
onMounted(() => {
  fetchComments()
})
</script>
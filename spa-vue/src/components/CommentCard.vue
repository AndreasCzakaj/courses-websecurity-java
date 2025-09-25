<template>
  <v-card
    class="mb-3"
    elevation="2"
  >
    <v-card-text>
      <div class="d-flex justify-space-between align-center mb-2">
        <div class="d-flex align-center">
          <v-avatar
            color="primary"
            size="32"
            class="mr-3"
          >
            <v-icon>mdi-account</v-icon>
          </v-avatar>
          <div>
            <div class="font-weight-medium">{{ comment.author }}</div>
            <div class="text-caption text-grey">{{ formattedDate }}</div>
          </div>
        </div>

        <v-btn
          v-if="showDelete"
          icon="mdi-delete"
          size="small"
          color="error"
          variant="text"
          @click="$emit('delete', comment.id)"
        ></v-btn>
      </div>

      <!-- SAFE: Vue templates automatically escape HTML content -->
      <div class="comment-content">{{ comment.content }}</div>
      <!-- DANGEROUS: Renders raw HTML -->
      <!--div class="comment-content" v-html="comment.content"></div-->


      <!-- Visual indicator for potentially malicious content -->
      <v-chip
        v-if="isPotentiallyMalicious"
        color="warning"
        size="small"
        class="mt-2"
      >
        <v-icon start>mdi-security</v-icon>
        Contains HTML/Script tags (safely escaped)
      </v-chip>
    </v-card-text>
  </v-card>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import type { Comment } from '@/types/comment'

interface Props {
  comment: Comment
  showDelete?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  showDelete: false
})

defineEmits<{
  delete: [id: number]
}>()

const formattedDate = computed(() => {
  return new Date(props.comment.createdAt).toLocaleString()
})

const isPotentiallyMalicious = computed(() => {
  const content = props.comment.content.toLowerCase()
  return content.includes('<script>') ||
         content.includes('<img') ||
         content.includes('onerror') ||
         content.includes('javascript:') ||
         content.includes('onclick')
})
</script>

<style scoped>
.comment-content {
  white-space: pre-wrap;
  word-break: break-word;
}
</style>
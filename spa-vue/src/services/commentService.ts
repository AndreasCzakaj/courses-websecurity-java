import axios from 'axios'
import type { Comment, CreateCommentRequest } from '@/types/comment'

const API_BASE_URL = '/api'

// Create axios instance with default config
const apiClient = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
})

// Add request interceptor for logging
apiClient.interceptors.request.use(
  (config) => {
    console.log(`Making ${config.method?.toUpperCase()} request to ${config.url}`)
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

// Add response interceptor for error handling
apiClient.interceptors.response.use(
  (response) => {
    return response
  },
  (error) => {
    console.error('API Error:', error.response?.data || error.message)
    return Promise.reject(error)
  }
)

export const commentService = {
  /**
   * Fetch all comments from the backend
   */
  async getComments(): Promise<Comment[]> {
    try {
      const response = await apiClient.get<Comment[]>('/comments')
      return response.data
    } catch (error) {
      console.error('Error fetching comments:', error)
      throw new Error('Failed to fetch comments')
    }
  },

  /**
   * Create a new comment
   */
  async createComment(comment: CreateCommentRequest): Promise<Comment> {
    try {
      // Basic validation
      if (!comment.content.trim()) {
        throw new Error('Comment content is required')
      }
      if (!comment.author.trim()) {
        throw new Error('Author name is required')
      }

      const response = await apiClient.post<Comment>('/comments', comment)
      return response.data
    } catch (error) {
      console.error('Error creating comment:', error)
      if (axios.isAxiosError(error) && error.response) {
        throw new Error(`Failed to create comment: ${error.response.status}`)
      }
      throw new Error('Failed to create comment')
    }
  },

  /**
   * Delete a comment by ID
   */
  async deleteComment(id: number): Promise<void> {
    try {
      await apiClient.delete(`/comments/${id}`)
    } catch (error) {
      console.error('Error deleting comment:', error)
      throw new Error('Failed to delete comment')
    }
  },
}
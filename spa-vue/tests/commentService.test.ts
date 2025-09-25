import { describe, it, expect, vi, beforeEach } from 'vitest'
import axios from 'axios'
import { commentService } from '@/services/commentService'
import type { Comment, CreateCommentRequest } from '@/types/comment'

// Mock axios
vi.mock('axios')
const mockedAxios = vi.mocked(axios)

const mockAxiosInstance = {
  get: vi.fn(),
  post: vi.fn(),
  delete: vi.fn(),
  interceptors: {
    request: { use: vi.fn() },
    response: { use: vi.fn() }
  }
}

describe('CommentService', () => {
  beforeEach(() => {
    vi.clearAllMocks()
    mockedAxios.create.mockReturnValue(mockAxiosInstance as any)
  })

  describe('getComments', () => {
    it('should fetch comments successfully', async () => {
      const mockComments: Comment[] = [
        {
          id: 1,
          content: 'Test comment',
          author: 'Test User',
          createdAt: '2024-01-01T00:00:00Z'
        }
      ]

      mockAxiosInstance.get.mockResolvedValue({ data: mockComments })

      const result = await commentService.getComments()

      expect(mockAxiosInstance.get).toHaveBeenCalledWith('/comments')
      expect(result).toEqual(mockComments)
    })

    it('should throw error when fetch fails', async () => {
      mockAxiosInstance.get.mockRejectedValue(new Error('Network error'))

      await expect(commentService.getComments()).rejects.toThrow('Failed to fetch comments')
    })
  })

  describe('createComment', () => {
    it('should create comment successfully', async () => {
      const commentRequest: CreateCommentRequest = {
        content: 'New comment',
        author: 'New User'
      }

      const mockResponse: Comment = {
        id: 1,
        ...commentRequest,
        createdAt: '2024-01-01T00:00:00Z'
      }

      mockAxiosInstance.post.mockResolvedValue({ data: mockResponse })

      const result = await commentService.createComment(commentRequest)

      expect(mockAxiosInstance.post).toHaveBeenCalledWith('/comments', commentRequest)
      expect(result).toEqual(mockResponse)
    })

    it('should throw error for empty content', async () => {
      const commentRequest: CreateCommentRequest = {
        content: '   ',
        author: 'User'
      }

      await expect(commentService.createComment(commentRequest)).rejects.toThrow('Comment content is required')
    })

    it('should throw error for empty author', async () => {
      const commentRequest: CreateCommentRequest = {
        content: 'Content',
        author: '   '
      }

      await expect(commentService.createComment(commentRequest)).rejects.toThrow('Author name is required')
    })
  })

  describe('deleteComment', () => {
    it('should delete comment successfully', async () => {
      mockAxiosInstance.delete.mockResolvedValue({})

      await commentService.deleteComment(1)

      expect(mockAxiosInstance.delete).toHaveBeenCalledWith('/comments/1')
    })

    it('should throw error when delete fails', async () => {
      mockAxiosInstance.delete.mockRejectedValue(new Error('Network error'))

      await expect(commentService.deleteComment(1)).rejects.toThrow('Failed to delete comment')
    })
  })
})
package ru.otus.repo

interface ICommentRepository {
	suspend fun createComment(request: DbCommentRequest): DbCommentResponse
	suspend fun getAllComments(): DbCommentsResponse
	suspend fun getByIdComments(request: DbCommentIdRequest): DbCommentResponse
	suspend fun updateComment(request: DbCommentRequest): DbCommentResponse
	suspend fun deleteComment(request: DbCommentIdRequest): DbCommentResponse

	companion object {
		val NONE = object : ICommentRepository {
			override suspend fun createComment(request: DbCommentRequest): DbCommentResponse {
				TODO("Not yet implemented")
			}

			override suspend fun getAllComments(): DbCommentsResponse {
				TODO("Not yet implemented")
			}

			override suspend fun getByIdComments(request: DbCommentIdRequest): DbCommentResponse {
				TODO("Not yet implemented")
			}

			override suspend fun updateComment(request: DbCommentRequest): DbCommentResponse {
				TODO("Not yet implemented")
			}

			override suspend fun deleteComment(request: DbCommentIdRequest): DbCommentResponse {
				TODO("Not yet implemented")
			}
		}
	}
}
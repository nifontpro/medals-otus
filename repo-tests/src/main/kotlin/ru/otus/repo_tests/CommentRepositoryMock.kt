package ru.otus.repo_tests

import ru.otus.repo.*

class CommentRepositoryMock(
	private val invokeCreateComment: (DbCommentRequest) -> DbCommentResponse = { DbCommentResponse.MOCK_SUCCESS_EMPTY },
	private val invokeGetByIdComment: (DbCommentIdRequest) -> DbCommentResponse = { DbCommentResponse.MOCK_SUCCESS_EMPTY },
	private val invokeGetAllComments: () -> DbCommentsResponse = { DbCommentsResponse.MOCK_SUCCESS_EMPTY },
	private val invokeUpdateComment: (DbCommentRequest) -> DbCommentResponse = { DbCommentResponse.MOCK_SUCCESS_EMPTY },
	private val invokeDeleteComment: (DbCommentIdRequest) -> DbCommentResponse = { DbCommentResponse.MOCK_SUCCESS_EMPTY },
) : ICommentRepository {
	override suspend fun createComment(request: DbCommentRequest): DbCommentResponse {
		return invokeCreateComment(request)
	}

	override suspend fun deleteComment(request: DbCommentIdRequest): DbCommentResponse {
		return invokeDeleteComment(request)
	}

	override suspend fun updateComment(request: DbCommentRequest): DbCommentResponse {
		return invokeUpdateComment(request)
	}

	override suspend fun getByIdComment(request: DbCommentIdRequest): DbCommentResponse {
		return invokeGetByIdComment(request)
	}

	override suspend fun getAllComments(): DbCommentsResponse {
		return invokeGetAllComments()
	}
}

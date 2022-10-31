package ru.otus.repo_stubs

import ru.otus.repo.*
import ru.otus.stubs.CommentStub

class CommentRepoStub() : ICommentRepository {
	override suspend fun createComment(request: DbCommentRequest): DbCommentResponse {
		return DbCommentResponse(
			data = CommentStub.prepareResult { },
			isSuccess = true,
		)
	}

	override suspend fun getByIdComment(request: DbCommentIdRequest): DbCommentResponse {
		return DbCommentResponse(
			data = CommentStub.prepareResult { },
			isSuccess = true,
		)
	}

	override suspend fun updateComment(request: DbCommentRequest): DbCommentResponse {
		return DbCommentResponse(
			data = CommentStub.prepareResult { },
			isSuccess = true,
		)
	}

	override suspend fun deleteComment(request: DbCommentIdRequest): DbCommentResponse {
		return DbCommentResponse(
			data = CommentStub.prepareResult { },
			isSuccess = true,
		)
	}

	override suspend fun getAllComments(): DbCommentsResponse {
		return DbCommentsResponse(
			data = CommentStub.getAll(),
			isSuccess = true,
		)
	}
}

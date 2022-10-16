package ru.otus.repo

import ru.otus.model.MkplComment
import ru.otus.model.MkplError

data class DbCommentResponse(
	override val data: MkplComment?,
	override val isSuccess: Boolean,
	override val errors: List<MkplError> = emptyList()
) : IDbResponse<MkplComment> {

	companion object {
		val MOCK_SUCCESS_EMPTY = DbCommentResponse(null, true)
		val MOCK_SUCCESS_NONE get() = DbCommentResponse(MkplComment.NONE, true)
	}
}

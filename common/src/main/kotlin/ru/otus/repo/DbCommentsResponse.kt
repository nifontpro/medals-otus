package ru.otus.repo

import ru.otus.model.MkplComment
import ru.otus.model.MkplError

data class DbCommentsResponse(
	override val data: List<MkplComment>?,
	override val isSuccess: Boolean,
	override val errors: List<MkplError> = emptyList()
) : IDbResponse<List<MkplComment>> {

	companion object {
		val MOCK_SUCCESS_EMPTY = DbCommentsResponse(emptyList(), true)
		val MOCK_SUCCESS_NONE get() = DbCommentsResponse(listOf(MkplComment.NONE), true)
	}
}

package ru.otus.stubs

import ru.otus.model.MkplComment
import ru.otus.stubs.CommentStubEx1.COMMENT_AD_1
import ru.otus.stubs.CommentStubEx1.COMMENT_USER_1

object CommentStub {
	fun get(): MkplComment = COMMENT_AD_1.copy()

	fun getAll(): List<MkplComment> = listOf(
		COMMENT_AD_1,
		COMMENT_USER_1
	)

	fun prepareResult(block: MkplComment.() -> Unit): MkplComment = get().apply(block)

}
package ru.otus.repo

import ru.otus.model.MkplComment
import ru.otus.model.MkplCommentId
import ru.otus.model.MkplLock

data class DbCommentIdRequest(
	val id: MkplCommentId,
	val lock: MkplLock = MkplLock.NONE,
) {
	constructor(comment: MkplComment) : this(comment.id, comment.lock)
}

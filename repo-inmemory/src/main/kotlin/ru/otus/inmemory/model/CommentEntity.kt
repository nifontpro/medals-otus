package ru.otus.inmemory.model

import ru.otus.model.*

data class CommentEntity(
	val id: String? = null,
	val entityId: String? = null,
	val entityType: String? = null,
	val comment: String? = null,
	val rating: Int? = null,
	val lock: String? = null
) {
	constructor(model: MkplComment) : this(
		id = model.id.asString().takeIf { it.isNotBlank() },
		entityId = model.entityId.asString().takeIf { it.isNotBlank() },
		entityType = model.entityType.takeIf { it != MkplEntityType.NONE }?.name,
		comment = model.comment.takeIf { it.isNotBlank() },
		rating = model.rating.takeIf { it != -1 },
		lock = model.lock.asString().takeIf { it.isNotBlank() }
	)

	fun toInternal() = MkplComment(
		id = id?.let { MkplCommentId(it) } ?: MkplCommentId.NONE,
		entityId = entityId?.let { MkplEntityId(it) } ?: MkplEntityId.NONE,
		entityType = entityType?.let { MkplEntityType.valueOf(it) } ?: MkplEntityType.NONE,
		comment = comment ?: "",
		rating = rating ?: -1,
		lock = lock?.let { MkplLock(it) } ?: MkplLock.NONE
	)
}
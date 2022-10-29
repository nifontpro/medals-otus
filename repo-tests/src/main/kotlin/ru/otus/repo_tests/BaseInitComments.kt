package ru.otus.repo_tests

import ru.otus.model.*

abstract class BaseInitComments(private val op: String) : IInitObjects<MkplComment> {

	open val lockOld: MkplLock = MkplLock("20000000-0000-0000-0000-000000000001")
	open val lockBad: MkplLock = MkplLock("20000000-0000-0000-0000-000000000009")

	fun createInitTestModel(
		suf: String,
		entityId: MkplEntityId = MkplEntityId("entity-123"),
		entityType: MkplEntityType = MkplEntityType.AD,
		lock: MkplLock = lockOld,
	) = MkplComment(
		id = MkplCommentId("comment-repo-$op-$suf"),
		comment = "$suf stub",
		rating = 5,
		entityId = entityId,
		entityType = entityType,
		lock = lock,
	)
}

package ru.otus.bussines.stubs

import ru.otus.MkplContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker
import ru.otus.model.MkplEntityId
import ru.otus.model.MkplEntityType
import ru.otus.model.MkplState
import ru.otus.stubs.CommentStub
import ru.otus.stubs.MkplStubs

fun ICorChainDsl<MkplContext>.stubCreateSuccess(title: String) = worker {
	this.title = title
	on { stubCase == MkplStubs.SUCCESS && state == MkplState.RUNNING }
	handle {
		state = MkplState.FINISHING
		val stub = CommentStub.prepareResult {

			commentRequest.comment.takeIf { it.isNotBlank() }?.also { this.comment = it }
			rating = commentRequest.rating
			commentRequest.entityId.takeIf { it != MkplEntityId.NONE }?.also { this.entityId = it }
			commentRequest.entityType.takeIf { it != MkplEntityType.NONE }?.also { this.entityType = it }
		}
		commentResponse = stub
	}
}

package ru.otus.bussines.repo

import ru.otus.MkplContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker
import ru.otus.model.MkplState
import ru.otus.repo.DbCommentRequest


fun ICorChainDsl<MkplContext>.repoUpdate(title: String) = worker {
	this.title = title
	on { state == MkplState.RUNNING }
	handle {
		val request = DbCommentRequest(
			commentRepoPrepare.deepCopy().apply {
				comment = commentValidated.comment
				rating = commentValidated.rating
				entityType = commentValidated.entityType
				entityId = commentValidated.entityId
			}
		)
		val result = repository.updateComment(request)
		val resultAd = result.data
		if (result.isSuccess && resultAd != null) {
			commentRepoDone = resultAd
		} else {
			state = MkplState.FAILING
			errors.addAll(result.errors)
		}
	}
}

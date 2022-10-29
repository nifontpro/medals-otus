package ru.otus.bussines.repo

import ru.otus.MkplContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker
import ru.otus.model.MkplState
import ru.otus.repo.DbCommentIdRequest


fun ICorChainDsl<MkplContext>.repoGetById(title: String) = worker {
	this.title = title
	description = "Чтение комментария из БД"
	on { state == MkplState.RUNNING }
	handle {
		val request = DbCommentIdRequest(commentValidated)
		val result = repository.getByIdComment(request)
		val resultAd = result.data
		if (result.isSuccess && resultAd != null) {
			commentRepoRead = resultAd
		} else {
			state = MkplState.FAILING
			errors.addAll(result.errors)
		}
	}
}

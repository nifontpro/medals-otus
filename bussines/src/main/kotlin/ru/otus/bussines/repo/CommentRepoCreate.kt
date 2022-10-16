package ru.otus.bussines.repo

import ru.otus.MkplContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker
import ru.otus.model.MkplState
import ru.otus.repo.DbCommentRequest


fun ICorChainDsl<MkplContext>.repoCreate(title: String) = worker {
	this.title = title
	description = "Добавление объявления в БД"
	on { state == MkplState.RUNNING }
	handle {
		val request = DbCommentRequest(commentRepoPrepare)
		val result = repository.createComment(request)
		val resultAd = result.data
		if (result.isSuccess && resultAd != null) {
			commentRepoDone = resultAd
		} else {
			state = MkplState.FAILING
			errors.addAll(result.errors)
		}
	}
}

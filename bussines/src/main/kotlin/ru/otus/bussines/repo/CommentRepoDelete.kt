package ru.otus.bussines.repo

import ru.otus.MkplContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker
import ru.otus.model.MkplState
import ru.otus.repo.DbCommentIdRequest

fun ICorChainDsl<MkplContext>.repoDelete(title: String) = worker {
	this.title = title
	description = "Удаление объявления из БД по ID"
	on { state == MkplState.RUNNING }
	handle {
		val request = DbCommentIdRequest(commentRepoPrepare)
		val result = repository.deleteComment(request)
		if (!result.isSuccess) {
			state = MkplState.FAILING
			errors.addAll(result.errors)
		}
		commentRepoDone = commentRepoRead
	}
}

package ru.otus.bussines.repo

import ru.otus.MkplContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker
import ru.otus.model.MkplState


fun ICorChainDsl<MkplContext>.repoGetAll(title: String) = worker {
	this.title = title
	description = "Чтение комментариев из БД"
	on { state == MkplState.RUNNING }
	handle {
		val result = repository.getAllComments()
		val resultComments = result.data
		if (result.isSuccess && resultComments != null) {
			commentsRepoDone = resultComments.toMutableList()
		} else {
			state = MkplState.FAILING
			errors.addAll(result.errors)
		}
	}
}

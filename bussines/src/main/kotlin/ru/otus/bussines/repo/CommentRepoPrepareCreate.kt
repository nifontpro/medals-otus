package ru.otus.bussines.repo

import ru.otus.MkplContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker
import ru.otus.model.MkplState

fun ICorChainDsl<MkplContext>.repoPrepareCreate(title: String) = worker {
	this.title = title
	description = "Подготовка объекта к сохранению в базе данных"
	on { state == MkplState.RUNNING }
	handle {
		commentRepoPrepare = commentValidated.deepCopy()
	}
}

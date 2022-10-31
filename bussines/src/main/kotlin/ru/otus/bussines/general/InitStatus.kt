package ru.otus.bussines.general

import ru.otus.MkplContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker
import ru.otus.model.MkplState

fun ICorChainDsl<MkplContext>.initStatus(title: String) = worker {
	this.title = title
	on { state == MkplState.NONE }
	handle { state = MkplState.RUNNING }
}

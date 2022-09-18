package ru.otus.bussines.validation

import ru.otus.MkplContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker
import ru.otus.model.MkplState

fun ICorChainDsl<MkplContext>.finishValidation(title: String) = worker {
	this.title = title
	on { state == MkplState.RUNNING }
	handle {
		commentValidated = commentValidating
	}
}
package ru.otus.bussines.general

import ru.otus.MkplContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker
import ru.otus.model.MkplState
import ru.otus.model.MkplWorkMode

fun ICorChainDsl<MkplContext>.prepareResult(title: String) = worker {
	this.title = title
	description = "Подготовка данных для ответа клиенту на запрос"
	on { workMode != MkplWorkMode.STUB }
	handle {
		commentResponse = commentRepoDone
		commentsResponse = commentsRepoDone
		state = when (val st = state) {
			MkplState.RUNNING -> MkplState.FINISHING
			else -> st
		}
	}
}

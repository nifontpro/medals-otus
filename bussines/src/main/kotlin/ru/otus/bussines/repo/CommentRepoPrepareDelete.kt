package ru.otus.bussines.repo

import ru.otus.MkplContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker
import ru.otus.model.MkplState


fun ICorChainDsl<MkplContext>.repoPrepareDelete(title: String) = worker {
	this.title = title
	description = """
        Готовим данные к удалению из БД
    """.trimIndent()
	on { state == MkplState.RUNNING }
	handle {
		commentRepoPrepare = commentValidated.deepCopy()
	}
}

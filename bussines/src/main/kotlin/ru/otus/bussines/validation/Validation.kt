package ru.otus.bussines.validation

import ru.otus.MkplContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.chain
import ru.otus.cor.worker
import ru.otus.model.MkplState

fun ICorChainDsl<MkplContext>.validation(block: ICorChainDsl<MkplContext>.() -> Unit) = chain {
	title = "Валидация"
	on { state == MkplState.RUNNING }

	worker("Копируем поля в commentValidation") { commentValidating = commentRequest.deepCopy() }
	block()
	finishValidation("Завершение проверок")
}

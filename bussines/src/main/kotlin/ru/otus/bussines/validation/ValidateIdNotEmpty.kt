package ru.otus.bussines.validation

import ru.otus.MkplContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker
import ru.otus.helpers.errorValidation
import ru.otus.helpers.fail

fun ICorChainDsl<MkplContext>.validateIdNotEmpty(title: String) = worker {
	this.title = title
	on { commentValidating.id.asString().isEmpty() }
	handle {
		fail(
			errorValidation(
				field = "id",
				violationCode = "empty",
				description = "field must not be empty"
			)
		)
	}
}

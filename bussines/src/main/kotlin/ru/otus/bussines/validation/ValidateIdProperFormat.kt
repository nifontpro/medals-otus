package ru.otus.bussines.validation

import ru.otus.MkplContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker
import ru.otus.helpers.errorValidation
import ru.otus.helpers.fail
import ru.otus.model.MkplCommentId

fun ICorChainDsl<MkplContext>.validateIdProperFormat(title: String) = worker {
	this.title = title

	// Может быть вынесен в MkplAdId для реализации различных форматов
	val regExp = Regex("^[0-9a-zA-Z-]+$")
	on { commentValidating.id != MkplCommentId.NONE && !commentValidating.id.asString().matches(regExp) }
	handle {
		val encodedId = commentValidating.id.asString()
			.replace("<", "&lt;")
			.replace(">", "&gt;")
		fail(
			errorValidation(
				field = "id",
				violationCode = "badFormat",
				description = "value $encodedId must contain only"
			)
		)
	}
}

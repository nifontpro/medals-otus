package ru.otus.bussines.validation

import ru.otus.MkplContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker
import ru.otus.helpers.errorValidation
import ru.otus.helpers.fail

fun ICorChainDsl<MkplContext>.validateCommentHasContent(title: String) = worker {
	this.title = title
	val regExp = Regex("\\p{L}")
	on { commentValidating.comment.isNotEmpty() && !commentValidating.comment.contains(regExp) }
	handle {
		fail(
			errorValidation(
				field = "comment",
				violationCode = "noContent",
				description = "field must contain leters"
			)
		)
	}
}

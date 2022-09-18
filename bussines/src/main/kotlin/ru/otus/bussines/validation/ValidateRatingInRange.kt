package ru.otus.bussines.validation

import ru.otus.MkplContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker
import ru.otus.helpers.errorValidation
import ru.otus.helpers.fail

fun ICorChainDsl<MkplContext>.validateRatingInRange(title: String) = worker {
	this.title = title
	on { commentValidating.rating < 0 || commentValidating.rating > 10 }
	handle {
		fail(
			errorValidation(
				field = "rating",
				violationCode = "exclude",
				description = "field must be in range from 0 to 10"
			)
		)
	}
}

package ru.otus.bussines.workers

import ru.otus.MkplContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker
import ru.otus.model.MkplError
import ru.otus.model.MkplState
import ru.otus.stubs.MkplStubs

fun ICorChainDsl<MkplContext>.stubValidationBadId(title: String) = worker {
	this.title = title
	on { stubCase == MkplStubs.BAD_ID && state == MkplState.RUNNING }
	handle {
		state = MkplState.FAILING
		this.errors.add(
			MkplError(
				group = "validation",
				code = "validation-id",
				field = "id",
				message = "Wrong id field"
			)
		)
	}
}

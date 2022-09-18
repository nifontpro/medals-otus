package ru.otus.bussines.stubs

import ru.otus.MkplContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker
import ru.otus.model.MkplError
import ru.otus.model.MkplState
import ru.otus.stubs.MkplStubs

fun ICorChainDsl<MkplContext>.stubValidationBadComment(title: String) = worker {
    this.title = title
    on { stubCase == MkplStubs.BAD_COMMENT && state == MkplState.RUNNING }
    handle {
        state = MkplState.FAILING
        this.errors.add(
            MkplError(
                group = "validation",
                code = "validation-comment",
                field = "comment",
                message = "Wrong comment field"
            )
        )
    }
}

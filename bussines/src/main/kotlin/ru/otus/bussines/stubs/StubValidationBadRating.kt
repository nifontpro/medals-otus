package ru.otus.bussines.stubs

import ru.otus.MkplContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker
import ru.otus.model.MkplError
import ru.otus.model.MkplState
import ru.otus.stubs.MkplStubs

fun ICorChainDsl<MkplContext>.stubValidationBadRating(title: String) = worker {
    this.title = title
    on { stubCase == MkplStubs.BAD_RATING && state == MkplState.RUNNING }
    handle {
        state = MkplState.FAILING
        this.errors.add(
            MkplError(
                group = "validation",
                code = "validation-rating",
                field = "rating",
                message = "Wrong rating field"
            )
        )
    }
}

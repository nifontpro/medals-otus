package ru.otus.bussines.workers

import ru.otus.MkplContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker
import ru.otus.model.MkplError
import ru.otus.model.MkplState
import ru.otus.stubs.MkplStubs

fun ICorChainDsl<MkplContext>.stubDbError(title: String) = worker {
    this.title = title
    on { stubCase == MkplStubs.DB_ERROR && state == MkplState.RUNNING }
    handle {
        state = MkplState.FAILING
        this.errors.add(
            MkplError(
                group = "internal",
                code = "internal-db",
                message = "Internal error"
            )
        )
    }
}

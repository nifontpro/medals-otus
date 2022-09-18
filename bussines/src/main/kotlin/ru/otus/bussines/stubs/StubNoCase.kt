package ru.otus.bussines.stubs

import ru.otus.MkplContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker
import ru.otus.helpers.fail
import ru.otus.model.MkplError
import ru.otus.model.MkplState

fun ICorChainDsl<MkplContext>.stubNoCase(title: String) = worker {
    this.title = title
    on { state == MkplState.RUNNING }
    handle {
        fail(
            MkplError(
                code = "validation",
                field = "stub",
                group = "validation",
                message = "Wrong stub case is requested: ${stubCase.name}"
            )
        )
    }
}

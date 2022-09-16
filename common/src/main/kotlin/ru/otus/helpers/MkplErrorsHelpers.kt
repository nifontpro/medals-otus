package ru.otus.helpers

import ru.otus.MkplContext
import ru.otus.model.MkplError
import ru.otus.model.MkplState

fun Throwable.asMkplError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = MkplError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)

fun MkplContext.addError(error: MkplError) = errors.add(error)
fun MkplContext.fail(error: MkplError) {
    addError(error)
    state = MkplState.FAILING
}

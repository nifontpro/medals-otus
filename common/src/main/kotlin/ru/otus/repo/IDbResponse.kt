package ru.otus.repo

import ru.otus.model.MkplError

interface IDbResponse<T> {
    val data: T?
    val isSuccess: Boolean
    val errors: List<MkplError>
}

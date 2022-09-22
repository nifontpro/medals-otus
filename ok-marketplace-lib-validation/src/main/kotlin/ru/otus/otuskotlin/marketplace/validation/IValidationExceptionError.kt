package ru.otus.otuskotlin.marketplace.validation

@Suppress("unused")
interface IValidationExceptionError: IValidationError {
    val exception: Throwable
}

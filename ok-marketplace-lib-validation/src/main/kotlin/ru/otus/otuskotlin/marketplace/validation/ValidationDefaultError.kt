package ru.otus.otuskotlin.marketplace.validation

@Suppress("unused")
data class ValidationDefaultError(
    override val message: String,
) : IValidationError

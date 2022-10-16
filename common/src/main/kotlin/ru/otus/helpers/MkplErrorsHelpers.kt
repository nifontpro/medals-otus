package ru.otus.helpers

import ru.otus.MkplContext
import ru.otus.exceptions.RepoConcurrencyException
import ru.otus.model.MkplError
import ru.otus.model.MkplLock
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

fun errorValidation(
	field: String,
	/**
	 * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
	 * Например: empty, badSymbols, tooLong, etc
	 */
	violationCode: String,
	description: String,
	level: MkplError.Levels = MkplError.Levels.ERROR,
) = MkplError(
	code = "validation-$field-$violationCode",
	field = field,
	group = "validation",
	message = "Validation error for field $field: $description",
	level = level,
)

fun errorMapping(
	field: String,
	/**
	 * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
	 * Например: empty, badSymbols, tooLong, etc
	 */
	violationCode: String,
	description: String,
	level: MkplError.Levels = MkplError.Levels.ERROR,
) = MkplError(
	code = "mapping-$field-$violationCode",
	field = field,
	group = "mapping",
	message = "Mapping error for field $field: $description",
	level = level,
)

fun errorAdministration(
	/**
	 * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
	 * Например: empty, badSymbols, tooLong, etc
	 */
	field: String = "",
	violationCode: String,
	description: String,
	exception: Exception? = null,
	level: MkplError.Levels = MkplError.Levels.ERROR,
) = MkplError(
	field = field,
	code = "administration-$violationCode",
	group = "administration",
	message = "Microservice management error: $description",
	level = level,
	exception = exception,
)

fun errorRepoConcurrency(
	expectedLock: MkplLock,
	actualLock: MkplLock?,
	exception: Exception? = null,
) = MkplError(
	field = "lock",
	code = "concurrency",
	group = "repo",
	message = "The object has been changed concurrently by another user or process",
	exception = exception ?: RepoConcurrencyException(expectedLock, actualLock),
)

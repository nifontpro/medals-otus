package ru.otus.bussines

import ru.otus.MkplContext
import ru.otus.bussines.general.initStatus
import ru.otus.bussines.general.operation
import ru.otus.bussines.stubs.*
import ru.otus.bussines.validation.*
import ru.otus.cor.rootChain
import ru.otus.cor.worker
import ru.otus.model.MkplCommand
import ru.otus.model.MkplCommentId

class CommentProcessor {
	suspend fun exec(ctx: MkplContext) = businessChain.exec(ctx)

	companion object {
		private val businessChain = rootChain {
			initStatus("Инициализация статуса")

			operation("Создание комментария", MkplCommand.CREATE) {
				stubs("Обработка стабов") {
					stubCreateSuccess("Имитация успешной обработки")
					stubValidationBadComment("Имитация ошибки валидации заголовка")
					stubValidationBadRating("Имитация ошибки валидации описания")
					stubDbError("Имитация ошибки работы с БД")
					stubNoCase("Ошибка: запрошенный стаб недопустим")
				}
				validation {
					worker("Очистка комментария") { commentValidating.comment = commentValidating.comment.trim() }
					validateCommentNotEmpty("Проверка, что описание не пусто")
					validateCommentHasContent("Проверка символов")
					validateRatingInRange("Проверка, что рейтинг в допустимых пределах")
				}
			}
			operation("Получить комментарий", MkplCommand.GET_BY_ID) {
				stubs("Обработка стабов") {
					stubReadSuccess("Имитация успешной обработки")
					stubValidationBadId("Имитация ошибки валидации id")
					stubDbError("Имитация ошибки работы с БД")
					stubNoCase("Ошибка: запрошенный стаб недопустим")
				}
				validation {
					worker("Очистка id") { commentValidating.id = MkplCommentId(commentValidating.id.asString().trim()) }
					validateIdNotEmpty("Проверка на непустой id")
					validateIdProperFormat("Проверка формата id")
				}
			}
			operation("Изменить комментарий", MkplCommand.UPDATE) {
				stubs("Обработка стабов") {
					stubUpdateSuccess("Имитация успешной обработки")
					stubValidationBadId("Имитация ошибки валидации id")
					stubValidationBadComment("Имитация ошибки валидации заголовка")
					stubValidationBadRating("Имитация ошибки валидации описания")
					stubDbError("Имитация ошибки работы с БД")
					stubNoCase("Ошибка: запрошенный стаб недопустим")
				}
				validation {
					worker("Очистка id") { commentValidating.id = MkplCommentId(commentValidating.id.asString().trim()) }
					worker("Очистка комментария") { commentValidating.comment = commentValidating.comment.trim() }
					validateIdNotEmpty("Проверка на непустой id")
					validateIdProperFormat("Проверка формата id")
					validateCommentNotEmpty("Проверка, что описание не пусто")
					validateCommentHasContent("Проверка символов")
					validateRatingInRange("Проверка, что рейтинг в допустимых пределах")
				}
			}
			operation("Удалить комментарий", MkplCommand.DELETE) {
				stubs("Обработка стабов") {
					stubDeleteSuccess("Имитация успешной обработки")
					stubValidationBadId("Имитация ошибки валидации id")
					stubDbError("Имитация ошибки работы с БД")
					stubNoCase("Ошибка: запрошенный стаб недопустим")
				}
				validation {
					worker("Очистка id") { commentValidating.id = MkplCommentId(commentValidating.id.asString().trim()) }
					validateIdNotEmpty("Проверка на непустой id")
					validateIdProperFormat("Проверка формата id")
				}
			}
			operation("Получить все комментарии", MkplCommand.GET_ALL) {
				stubs("Обработка стабов") {
					stubValidationBadId("Имитация ошибки валидации id")
					stubDbError("Имитация ошибки работы с БД")
					stubNoCase("Ошибка: запрошенный стаб недопустим")
				}
			}
		}.build()
	}
}

package ru.otus.bussines

import ru.otus.MkplContext
import ru.otus.bussines.groups.operation
import ru.otus.bussines.groups.stubs
import ru.otus.bussines.workers.*
import ru.otus.cor.rootChain
import ru.otus.model.MkplCommand

class CommentProcessor {
	suspend fun exec(ctx: MkplContext) = BusinessChain.exec(ctx)

	companion object {
		private val BusinessChain = rootChain {
			initStatus("Инициализация статуса")

			operation("Создание объявления", MkplCommand.CREATE) {
				stubs("Обработка стабов") {
					stubCreateSuccess("Имитация успешной обработки")
					stubValidationBadComment("Имитация ошибки валидации заголовка")
					stubValidationBadRating("Имитация ошибки валидации описания")
					stubDbError("Имитация ошибки работы с БД")
					stubNoCase("Ошибка: запрошенный стаб недопустим")
				}
			}
			operation("Получить объявление", MkplCommand.GET_BY_ID) {
				stubs("Обработка стабов") {
					stubReadSuccess("Имитация успешной обработки")
					stubValidationBadId("Имитация ошибки валидации id")
					stubDbError("Имитация ошибки работы с БД")
					stubNoCase("Ошибка: запрошенный стаб недопустим")
				}
			}
			operation("Изменить объявление", MkplCommand.UPDATE) {
				stubs("Обработка стабов") {
					stubUpdateSuccess("Имитация успешной обработки")
					stubValidationBadId("Имитация ошибки валидации id")
					stubValidationBadComment("Имитация ошибки валидации заголовка")
					stubValidationBadRating("Имитация ошибки валидации описания")
					stubDbError("Имитация ошибки работы с БД")
					stubNoCase("Ошибка: запрошенный стаб недопустим")
				}
			}
			operation("Удалить объявление", MkplCommand.DELETE) {
				stubs("Обработка стабов") {
					stubDeleteSuccess("Имитация успешной обработки")
					stubValidationBadId("Имитация ошибки валидации id")
					stubDbError("Имитация ошибки работы с БД")
					stubNoCase("Ошибка: запрошенный стаб недопустим")
				}
			}
			operation("Поиск объявлений", MkplCommand.GET_ALL) {
				stubs("Обработка стабов") {
					stubValidationBadId("Имитация ошибки валидации id")
					stubDbError("Имитация ошибки работы с БД")
					stubNoCase("Ошибка: запрошенный стаб недопустим")
				}
			}
		}.build()
	}
}

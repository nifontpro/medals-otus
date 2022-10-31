package ru.otus.bussines

import ru.otus.MkplContext
import ru.otus.bussines.general.initRepo
import ru.otus.bussines.general.initStatus
import ru.otus.bussines.general.operation
import ru.otus.bussines.general.prepareResult
import ru.otus.bussines.repo.*
import ru.otus.bussines.stubs.*
import ru.otus.bussines.validation.*
import ru.otus.cor.chain
import ru.otus.cor.rootChain
import ru.otus.cor.worker
import ru.otus.model.MkplCommand
import ru.otus.model.MkplCommentId
import ru.otus.model.MkplSettings
import ru.otus.model.MkplState

class CommentProcessor(private val settings: MkplSettings = MkplSettings()) {
	suspend fun exec(ctx: MkplContext) = businessChain.exec(ctx.apply { settings = this@CommentProcessor.settings })

	companion object {
		private val businessChain = rootChain {
			initStatus("Инициализация статуса")
			initRepo("Инициализация репозитория")

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
					finishValidation("Завершение проверок")
				}
				chain {
					title = "Логика сохранения"
					repoPrepareCreate("Подготовка объекта для сохранения")
					repoCreate("Создание комментария в БД")
				}
				prepareResult("Подготовка ответа")
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
					finishValidation("Завершение проверок")
				}
				chain {
					title = "Логика чтения"
					repoGetById("Чтение комментария из БД")
					worker {
						title = "Подготовка ответа для чтения по id"
						on { state == MkplState.RUNNING }
						handle { commentRepoDone = commentRepoRead }
					}
				}
				prepareResult("Подготовка ответа")
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
					finishValidation("Завершение проверок")
				}
				chain {
					title = "Логика обновления"
					repoGetById("Чтение комментария из БД")
					repoPrepareUpdate("Подготовка объекта для обновления")
					repoUpdate("Обновление комментария в БД")
				}
				prepareResult("Подготовка ответа")
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
					finishValidation("Завершение проверок")
				}
				chain {
					title = "Логика удаления"
					repoGetById("Чтение комментария из БД")
					repoPrepareDelete("Подготовка объекта для удаления")
					repoDelete("Удаление объявления из БД")
				}
				prepareResult("Подготовка ответа")
			}
			operation("Получить все комментарии", MkplCommand.GET_ALL) {
				stubs("Обработка стабов") {
					stubValidationBadId("Имитация ошибки валидации id")
					stubDbError("Имитация ошибки работы с БД")
					stubNoCase("Ошибка: запрошенный стаб недопустим")
				}
				chain {
					title = "Логика получения всех комментариев"
					repoGetAll("Чтение всех комментариев")
				}
				prepareResult("Подготовка ответа")
			}
		}.build()
	}
}

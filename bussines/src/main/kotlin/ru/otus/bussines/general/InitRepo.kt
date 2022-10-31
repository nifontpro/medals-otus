package ru.otus.bussines.general

import ru.otus.MkplContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker
import ru.otus.helpers.errorAdministration
import ru.otus.helpers.fail
import ru.otus.model.MkplWorkMode
import ru.otus.repo.ICommentRepository

fun ICorChainDsl<MkplContext>.initRepo(title: String) = worker {
	this.title = title
	description = """
        Вычисление основного рабочего репозитория в зависимости от запрошенного режима работы        
    """.trimIndent()
	handle {
		repository = when (workMode) {
			MkplWorkMode.TEST -> settings.repoTest
			MkplWorkMode.STUB -> settings.repoStub
			else -> settings.repoProd
		}
		if (workMode != MkplWorkMode.STUB && repository == ICommentRepository.NONE) fail(
			errorAdministration(
				field = "repo",
				violationCode = "dbNotConfigured",
				description = "The database is unconfigured for chosen workmode ($workMode). " +
						"Please, contact the administrator staff"
			)
		)
	}
}

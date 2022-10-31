package ru.otus.bussines.repo

import ru.otus.MkplContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker
import ru.otus.model.MkplState

fun ICorChainDsl<MkplContext>.repoPrepareUpdate(title: String) = worker {
	this.title = title
	description = "Готовим данные к сохранению в БД: совмещаем данные, прочитанные из БД, " +
			"и данные, полученные от пользователя"
	on { state == MkplState.RUNNING }
	handle {
		commentRepoPrepare = commentRepoRead.deepCopy().apply {
			comment = commentValidated.comment
			rating = commentValidated.rating
		}
	}
}

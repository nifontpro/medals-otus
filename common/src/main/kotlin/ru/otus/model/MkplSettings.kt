package ru.otus.model

import ru.otus.repo.ICommentRepository

data class MkplSettings(
	val repoStub: ICommentRepository = ICommentRepository.NONE,
	val repoTest: ICommentRepository = ICommentRepository.NONE,
	val repoProd: ICommentRepository = ICommentRepository.NONE,
)

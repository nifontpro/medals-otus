package ru.otus

import ru.otus.model.*
import ru.otus.repo.ICommentRepository
import ru.otus.stubs.MkplStubs

data class MkplContext(
	var settings: MkplSettings = MkplSettings(),
	var repository: ICommentRepository = ICommentRepository.NONE,

	var command: MkplCommand = MkplCommand.NONE,
	var state: MkplState = MkplState.NONE,
	val errors: MutableList<MkplError> = mutableListOf(),

	var workMode: MkplWorkMode = MkplWorkMode.PROD,
	var stubCase: MkplStubs = MkplStubs.NONE,

	var requestId: MkplRequestId = MkplRequestId.NONE,
	var timeStart: Long = System.currentTimeMillis(),
	var commentRequest: MkplComment = MkplComment(),
	var commentResponse: MkplComment = MkplComment(),
	var commentsResponse: MutableList<MkplComment> = mutableListOf(),

	var commentValidating: MkplComment = MkplComment(),
	var commentValidated: MkplComment = MkplComment(),

	var commentRepoRead: MkplComment = MkplComment(),
	var commentRepoPrepare: MkplComment = MkplComment(),
	var commentRepoDone: MkplComment = MkplComment(),
	var commentsRepoDone: MutableList<MkplComment> = mutableListOf(),
)

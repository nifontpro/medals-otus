package ru.otus.otuskotlin.marketplace.mappers.v1

import ru.otus.MkplContext
import ru.otus.api.v1.models.*
import ru.otus.model.*
import ru.otus.otuskotlin.marketplace.mappers.v1.exceptions.UnknownRequestClass
import ru.otus.stubs.MkplStubs

fun MkplContext.fromTransport(request: IRequest) = when (request) {
	is CommentCreateRequest -> fromTransport(request)
	is CommentGetAllRequest -> fromTransport(request)
	is CommentGetByIdRequest -> fromTransport(request)
	is CommentUpdateRequest -> fromTransport(request)
	is CommentDeleteRequest -> fromTransport(request)
	else -> throw UnknownRequestClass(request.javaClass)
}

private fun String?.toCommentId() = this?.let { MkplCommentId(it) } ?: MkplCommentId.NONE
private fun String?.toEntityId() = this?.let { MkplEntityId(it) } ?: MkplEntityId.NONE
private fun String?.toCommentWithId() = MkplComment(id = this.toCommentId())
private fun IRequest?.requestId() = this?.requestId?.let { MkplRequestId(it) } ?: MkplRequestId.NONE

private fun CommentDebug?.transportToWorkMode(): MkplWorkMode = when (this?.mode) {
	CommentRequestDebugMode.PROD -> MkplWorkMode.PROD
	CommentRequestDebugMode.TEST -> MkplWorkMode.TEST
	CommentRequestDebugMode.STUB -> MkplWorkMode.STUB
	null -> MkplWorkMode.PROD
}

private fun CommentDebug?.transportToStubCase(): MkplStubs = when (this?.stub) {
	CommentRequestDebugStubs.SUCCESS -> MkplStubs.SUCCESS
	CommentRequestDebugStubs.NOT_FOUND -> MkplStubs.NOT_FOUND
	CommentRequestDebugStubs.BAD_ID -> MkplStubs.BAD_ID
	CommentRequestDebugStubs.CANNOT_DELETE -> MkplStubs.CANNOT_DELETE
	CommentRequestDebugStubs.BAD_RATING -> MkplStubs.BAD_RATING
	null -> MkplStubs.NONE
}

fun MkplContext.fromTransport(request: CommentCreateRequest) {
	command = MkplCommand.CREATE
	requestId = request.requestId()
	commentRequest = request.comment?.toInternal() ?: MkplComment()
	workMode = request.debug.transportToWorkMode()
	stubCase = request.debug.transportToStubCase()
}

fun MkplContext.fromTransport(request: CommentGetAllRequest) {
	command = MkplCommand.GET_ALL
	requestId = request.requestId()
	commentRequest.entityType = request.type.fromTransport()
	workMode = request.debug.transportToWorkMode()
	stubCase = request.debug.transportToStubCase()
}

fun MkplContext.fromTransport(request: CommentUpdateRequest) {
	command = MkplCommand.UPDATE
	requestId = request.requestId()
	commentRequest = request.comment?.toInternal() ?: MkplComment()
	workMode = request.debug.transportToWorkMode()
	stubCase = request.debug.transportToStubCase()
}

fun MkplContext.fromTransport(request: CommentDeleteRequest) {
	command = MkplCommand.DELETE
	requestId = request.requestId()
	commentRequest = request.id.toCommentWithId()
	workMode = request.debug.transportToWorkMode()
	stubCase = request.debug.transportToStubCase()
}

fun MkplContext.fromTransport(request: CommentGetByIdRequest) {
	command = MkplCommand.GET_BY_ID
	requestId = request.requestId()
	commentRequest = request.id.toCommentWithId()
	workMode = request.debug.transportToWorkMode()
	stubCase = request.debug.transportToStubCase()
}

private fun CommentCreateObject.toInternal(): MkplComment = MkplComment(
	entityId = entityId.toEntityId(),
	comment = comment ?: "",
	rating = rating ?: 0,
	entityType = entityType.fromTransport()
)

private fun CommentUpdateObject.toInternal(): MkplComment = MkplComment(
	id = id.toCommentId(),
	entityId = entityId.toEntityId(),
	comment = comment ?: "",
	rating = rating ?: 0,
	entityType = entityType.fromTransport()
)

private fun EntityType?.fromTransport(): MkplEntityType = when (this) {
	EntityType.AD -> MkplEntityType.AD
	EntityType.USER -> MkplEntityType.USER
	null -> MkplEntityType.NONE
}


package ru.otus.otuskotlin.marketplace.mappers.v1

import ru.otus.MkplContext
import ru.otus.api.v1.models.*
import ru.otus.model.*
import ru.otus.otuskotlin.marketplace.mappers.v1.exceptions.UnknownMkplCommand

fun MkplContext.toTransportComment(): IResponse = when (val cmd = command) {
    MkplCommand.CREATE -> toTransportCreate()
    MkplCommand.GET_ALL -> toTransportGetAll()
    MkplCommand.GET_BY_ID -> toTransportGetById()
    MkplCommand.UPDATE -> toTransportUpdate()
    MkplCommand.DELETE -> toTransportDelete()
    MkplCommand.NONE -> throw UnknownMkplCommand(cmd)
}

fun MkplContext.toTransportInit() = CommentInitResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (errors.isEmpty()) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
)

fun MkplContext.toTransportCreate() = CommentCreateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == MkplState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    comment = commentResponse.toTransportComment()
)

fun MkplContext.toTransportGetAll() = CommentGetAllResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == MkplState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    comments = commentsResponse.toTransportComment()
)

fun MkplContext.toTransportGetById() = CommentGetByIdResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == MkplState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    comment = commentResponse.toTransportComment()
)

fun MkplContext.toTransportUpdate() = CommentUpdateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == MkplState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    comment = commentResponse.toTransportComment()
)

fun MkplContext.toTransportDelete() = CommentDeleteResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == MkplState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    comment = commentResponse.toTransportComment()
)

fun List<MkplComment>.toTransportComment(): List<CommentResponseObject>? = this
    .map { it.toTransportComment() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun MkplComment.toTransportComment(): CommentResponseObject = CommentResponseObject(
    entityId = entityId.takeIf { it != MkplEntityId.NONE }?.asString(),
    comment = comment.takeIf { it.isNotBlank() },
    rating = rating.takeIf { it != -1 },
    entityType = entityType.toTransportComment()
)

private fun MkplEntityType.toTransportComment(): EntityType? = when (this) {
    MkplEntityType.USER -> EntityType.USER
    MkplEntityType.AD -> EntityType.AD
    MkplEntityType.NONE -> null
}

private fun List<MkplError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportComment() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun MkplError.toTransportComment() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)

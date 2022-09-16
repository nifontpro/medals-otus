package ru.otus.v1

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.otus.MkplContext
import ru.otus.api.v1.models.*
import ru.otus.bussines.CommentProcessor
import ru.otus.otuskotlin.marketplace.mappers.v1.*

suspend inline fun <reified T : Any, reified R : Any> ApplicationCall.process(
	processor: CommentProcessor,
	fromTransport: MkplContext.(request: T) -> Unit,
	toTransport: MkplContext.() -> R
) {
	val ctx = MkplContext(
		timeStart = System.currentTimeMillis(),
	)
	val request = receive<T>()
	ctx.fromTransport(request)
	processor.exec(ctx)
	respond(ctx.toTransport())
}

suspend fun ApplicationCall.createComment(processor: CommentProcessor) =
	process<CommentCreateRequest, CommentCreateResponse>(processor, { fromTransport(it) }, { toTransportCreate() })

suspend fun ApplicationCall.getAllComment(processor: CommentProcessor) =
	process<CommentGetAllRequest, CommentGetAllResponse>(processor, { fromTransport(it) }, { toTransportGetAll() })

suspend fun ApplicationCall.updateComment(processor: CommentProcessor) =
	process<CommentUpdateRequest, CommentUpdateResponse>(processor, { fromTransport(it) }, { toTransportUpdate() })

suspend fun ApplicationCall.deleteComment(processor: CommentProcessor) =
	process<CommentDeleteRequest, CommentDeleteResponse>(processor, { fromTransport(it) }, { toTransportDelete() })

suspend fun ApplicationCall.getCommentById(processor: CommentProcessor) =
	process<CommentGetByIdRequest, CommentGetByIdResponse>(processor, { fromTransport(it) }, { toTransportGetById() })
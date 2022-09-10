package ru.otus.v1

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.otus.MkplContext
import ru.otus.api.v1.models.*
import ru.otus.otuskotlin.marketplace.mappers.v1.*
import ru.otus.stubs.CommentStub

suspend fun ApplicationCall.createComment() {
	val request = receive<CommentCreateRequest>()
	val context = MkplContext()
	context.fromTransport(request)
	context.commentResponse = CommentStub.get()
	respond(context.toTransportCreate())
}

suspend fun ApplicationCall.getAllComment() {
	val request = receive<CommentGetAllRequest>()
	val context = MkplContext()
	context.fromTransport(request)
	context.commentsResponse.addAll(CommentStub.getAll())
	respond(context.toTransportGetAll())
}

suspend fun ApplicationCall.getByIdComment() {
	val request = receive<CommentGetByIdRequest>()
	val context = MkplContext()
	context.fromTransport(request)
	context.commentResponse = CommentStub.get()
	respond(context.toTransportUpdate())
}

suspend fun ApplicationCall.deleteComment() {
	val request = receive<CommentDeleteRequest>()
	val context = MkplContext()
	context.fromTransport(request)
	context.commentResponse = CommentStub.get()
	respond(context.toTransportDelete())
}

suspend fun ApplicationCall.updateComment() {
	val request = receive<CommentUpdateRequest>()
	val context = MkplContext()
	context.fromTransport(request)
	context.commentResponse = CommentStub.get()
	respond(context.toTransportUpdate())
}

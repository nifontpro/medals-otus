package ru.otus.v1

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.otus.bussines.CommentProcessor

fun Route.v1Comment(processor: CommentProcessor) {
	route("comment") {
		post("create") {
			call.createComment(processor)
		}
		post("get_all") {
			call.getAllComment(processor)
		}
		post("get_id") {
			call.getCommentById(processor)
		}
		post("update") {
			call.updateComment(processor)
		}
		post("delete") {
			call.deleteComment(processor)
		}
	}
}
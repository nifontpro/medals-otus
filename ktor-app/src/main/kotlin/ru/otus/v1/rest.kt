package ru.otus.v1

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.v1Comment() {
	route("comment") {
		post("create") {
			call.createComment()
		}
		post("get_all") {
			call.getAllComment()
		}
		post("get_id") {
			call.getByIdComment()
		}
		post("update") {
			call.updateComment()
		}
		post("delete") {
			call.deleteComment()
		}
	}
}
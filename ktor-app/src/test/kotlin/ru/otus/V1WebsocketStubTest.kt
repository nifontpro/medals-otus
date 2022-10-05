package ru.otus

import io.ktor.client.plugins.websocket.*
import io.ktor.server.testing.*
import io.ktor.websocket.*
import kotlinx.coroutines.withTimeout
import ru.otus.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class V1WebsocketStubTest {

	@Test
	fun create() {
		testApplication {
//			application {
//				module()
//			}

			val client = createClient {
				install(WebSockets)
			}

			client.webSocket("/ws/v1") {
				withTimeout(3000) {
					val incame = incoming.receive()
					val response = apiV1Mapper.readValue((incame as Frame.Text).readText(), IResponse::class.java)
					assertIs<CommentInitResponse>(response)
				}
				val requestObj = CommentCreateRequest(
					requestId = "12345",
					comment = CommentCreateObject(
						entityId = "EA111",
						comment = "Хороший товар!",
						rating = 7,
						entityType = EntityType.AD,
					),
					debug = CommentDebug(
						mode = CommentRequestDebugMode.STUB,
						stub = CommentRequestDebugStubs.SUCCESS
					)
				)
				send(Frame.Text(apiV1Mapper.writeValueAsString(requestObj)))
				withTimeout(3000) {
					val incame = incoming.receive()
					val response = apiV1Mapper.readValue((incame as Frame.Text).readText(), CommentCreateResponse::class.java)

					assertEquals("comment_id", response.comment?.id)
					assertEquals("EA111", response.comment?.entityId)
					assertEquals(7, response.comment?.rating)
				}
			}
		}
	}

	@Test
	fun getById() {
		testApplication {

			val client = createClient {
				install(WebSockets)
			}

			client.webSocket("/ws/v1") {
				withTimeout(3000) {
					incoming.receive()
				}
				val requestObj = CommentGetByIdRequest(
					requestId = "12345",
					id = "comment_id",
					debug = CommentDebug(
						mode = CommentRequestDebugMode.STUB,
						stub = CommentRequestDebugStubs.SUCCESS
					)
				)
				send(Frame.Text(apiV1Mapper.writeValueAsString(requestObj)))
				withTimeout(3000) {
					val incame = incoming.receive()
					val response = apiV1Mapper.readValue((incame as Frame.Text).readText(), CommentGetByIdResponse::class.java)

					assertEquals("comment_id", response.comment?.id)
				}
			}
		}
	}

	@Test
	fun update() {
		testApplication {

			val client = createClient {
				install(WebSockets)
			}

			client.webSocket("/ws/v1") {
				withTimeout(3000) {
					incoming.receive()
				}
				val requestObj = CommentUpdateRequest(
					requestId = "12345",
					comment = CommentUpdateObject(
						id = "comment_id",
						entityId = "EA111",
						comment = "Плохой товар!",
						rating = 7,
						entityType = EntityType.AD,
					),
					debug = CommentDebug(
						mode = CommentRequestDebugMode.STUB,
						stub = CommentRequestDebugStubs.SUCCESS
					)
				)
				send(Frame.Text(apiV1Mapper.writeValueAsString(requestObj)))
				withTimeout(3000) {
					val incame = incoming.receive()
					val response = apiV1Mapper.readValue((incame as Frame.Text).readText(), CommentUpdateResponse::class.java)

					assertEquals("comment_id", response.comment?.id)
					assertEquals("Плохой товар!", response.comment?.comment)
				}
			}
		}
	}

	@Test
	fun delete() {
		testApplication {

			val client = createClient {
				install(WebSockets)
			}

			client.webSocket("/ws/v1") {
				withTimeout(3000) {
					incoming.receive()
				}
				val requestObj = CommentDeleteRequest(
					requestId = "12345",
					id = "comment_id",
					debug = CommentDebug(
						mode = CommentRequestDebugMode.STUB,
						stub = CommentRequestDebugStubs.SUCCESS
					)
				)
				send(Frame.Text(apiV1Mapper.writeValueAsString(requestObj)))
				withTimeout(3000) {
					val incame = incoming.receive()
					val response = apiV1Mapper.readValue((incame as Frame.Text).readText(), CommentDeleteResponse::class.java)

					assertEquals("comment_id", response.comment?.id)
				}
			}
		}
	}
}

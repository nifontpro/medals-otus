package ru.otus

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.testing.*
import org.junit.Test
import ru.otus.api.v1.models.*
import kotlin.test.assertEquals

class V1StubsTest {

	@Test
	fun create() = testApplication {
/*		application { // В моей конфигурации это приводит к повторной установке плагинов
			module()
		}*/
		val client = myClient()

		val response = client.post("/v1/comment/create") {
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
			contentType(ContentType.Application.Json)
			setBody(requestObj)
		}
		val responseObj = response.body<CommentCreateResponse>()
		println(responseObj)
		assertEquals(200, response.status.value)
		assertEquals("EA111", responseObj.comment?.entityId)
	}

	@Test
	fun delete() = testApplication {
		val client = myClient()

		val response = client.post("/v1/comment/delete") {
			val requestObj = CommentDeleteRequest(
				requestId = "12345",
				id = "comment_id",
				debug = CommentDebug(
					mode = CommentRequestDebugMode.STUB,
					stub = CommentRequestDebugStubs.SUCCESS
				)
			)
			contentType(ContentType.Application.Json)
			setBody(requestObj)
		}
		val responseObj = response.body<CommentDeleteResponse>()
		assertEquals(200, response.status.value)
		assertEquals("comment_id", responseObj.comment?.id)
	}

	@Test
	fun getAll() = testApplication {
		val client = myClient()

		val response = client.post("/v1/comment/get_all") {
			val requestObj = CommentGetAllRequest(
				requestId = "12345",
				debug = CommentDebug(
					mode = CommentRequestDebugMode.STUB,
					stub = CommentRequestDebugStubs.SUCCESS
				)
			)
			contentType(ContentType.Application.Json)
			setBody(requestObj)
		}

		val responseObj = response.body<CommentGetAllResponse>()
		assertEquals(200, response.status.value)
		assertEquals("EA111", responseObj.comments?.first()?.entityId)
	}

	private fun ApplicationTestBuilder.myClient() = createClient {
		install(ContentNegotiation) {
			jackson {
				disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
				enable(SerializationFeature.INDENT_OUTPUT)
				writerWithDefaultPrettyPrinter()
			}
		}
	}
}
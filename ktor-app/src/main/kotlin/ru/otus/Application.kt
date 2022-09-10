package ru.otus

import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.locations.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.cachingheaders.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.slf4j.event.Level
import ru.otus.v1.v1Comment

fun main(args: Array<String>): Unit =
	io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
fun Application.module() {

	install(CachingHeaders)
	install(DefaultHeaders)
	install(AutoHeadResponse)
	install(WebSockets)

	install(CORS) {
		allowMethod(HttpMethod.Options)
		allowMethod(HttpMethod.Put)
		allowMethod(HttpMethod.Delete)
		allowMethod(HttpMethod.Patch)
		allowHeader(HttpHeaders.Authorization)
		allowHeader("MyCustomHeader")
		allowCredentials = true
		anyHost() // TODO remove
	}

	install(ContentNegotiation) {
		jackson {
			setConfig(apiV1Mapper.serializationConfig)
			setConfig(apiV1Mapper.deserializationConfig)
		}
	}


	install(CallLogging) {
		level = Level.INFO
	}

	@Suppress("OPT_IN_USAGE")
	install(Locations)

	routing {
		get("/") {
			call.respondText("Hello, world!")
		}

		route("v1") {
			v1Comment()
		}

		static("static") {
			resources("static")
		}
	}
}

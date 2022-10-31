package ru.otus.v1

import io.ktor.websocket.*
import ru.otus.api.v1.models.IRequest
import ru.otus.apiV1Mapper
import ru.otus.base.KtorUserSession
import ru.otus.base.mpWsHandler
import ru.otus.bussines.CommentProcessor
import ru.otus.otuskotlin.marketplace.mappers.v1.fromTransport
import ru.otus.otuskotlin.marketplace.mappers.v1.toTransportComment
import ru.otus.otuskotlin.marketplace.mappers.v1.toTransportInit

suspend fun WebSocketSession.mpWsHandlerV1(
	processor: CommentProcessor,
	sessions: MutableSet<KtorUserSession>
) = this.mpWsHandler(
	processor = processor,
	sessions = sessions,
	fromTransport = { fromTransport(apiV1Mapper.readValue(it, IRequest::class.java)) },
	toTransportInit = { apiV1Mapper.writeValueAsString(toTransportInit()) },
	toTransportBiz = { apiV1Mapper.writeValueAsString(toTransportComment()) },
	apiVersion = "v1"
)

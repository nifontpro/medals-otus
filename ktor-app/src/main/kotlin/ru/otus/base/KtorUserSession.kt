package ru.otus.base

import io.ktor.websocket.*
import ru.otus.model.IClientSession

data class KtorUserSession(
	override val fwSession: WebSocketSession,
	override val apiVersion: String,
) : IClientSession<WebSocketSession>

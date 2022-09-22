package ru.otus.base

import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import ru.otus.MkplContext
import ru.otus.bussines.CommentProcessor
import ru.otus.helpers.addError
import ru.otus.helpers.asMkplError
import ru.otus.model.MkplCommand

suspend fun WebSocketSession.mpWsHandler(
	processor: CommentProcessor,
	sessions: MutableSet<KtorUserSession>,
	fromTransport: MkplContext.(request: String) -> Unit,
	toTransportBiz: MkplContext.() -> String,
	toTransportInit: MkplContext.() -> String,
	apiVersion: String,
) {
	val userSession = KtorUserSession(this, apiVersion)
	sessions.add(userSession)
	run {
		val ctx = MkplContext(
			timeStart = System.currentTimeMillis()
		)
		// обработка запроса на инициализацию
		outgoing.send(Frame.Text(ctx.toTransportInit()))
	}
	incoming
		.receiveAsFlow()
		.mapNotNull { it as? Frame.Text }
		.map { frame ->
			val ctx = MkplContext(
				timeStart = System.currentTimeMillis()
			)
			// Обработка исключений без завершения flow
			try {
				val jsonStr = frame.readText()
				ctx.fromTransport(jsonStr)
				processor.exec(ctx)
				// Если произошли изменения, то ответ отправляется всем
				if (ctx.isUpdatableCommand()) {
					sessions.filter { it.apiVersion == apiVersion }.forEach {
						it.fwSession.send(Frame.Text(ctx.toTransportBiz()))
					}
				} else {
					outgoing.send(Frame.Text(ctx.toTransportBiz()))
				}
			} catch (_: ClosedReceiveChannelException) {
				sessions.remove(userSession)
			} catch (t: Throwable) {
				ctx.addError(t.asMkplError())
				outgoing.send(Frame.Text(ctx.toTransportInit()))
			}
		}
		.collect()
}

private fun MkplContext.isUpdatableCommand() =
	this.command in listOf(MkplCommand.CREATE, MkplCommand.UPDATE, MkplCommand.DELETE)

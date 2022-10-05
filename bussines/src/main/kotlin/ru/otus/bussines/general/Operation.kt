package ru.otus.bussines.general

import ru.otus.MkplContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.chain
import ru.otus.model.MkplCommand
import ru.otus.model.MkplState

fun ICorChainDsl<MkplContext>.operation(
	title: String,
	command: MkplCommand,
	block: ICorChainDsl<MkplContext>.() -> Unit
) = chain {
	block()
	this.title = title
	on { this.command == command && state == MkplState.RUNNING }
}

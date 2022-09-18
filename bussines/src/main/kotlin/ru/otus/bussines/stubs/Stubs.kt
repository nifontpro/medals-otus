package ru.otus.bussines.stubs

import ru.otus.MkplContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.chain
import ru.otus.model.MkplState
import ru.otus.model.MkplWorkMode

fun ICorChainDsl<MkplContext>.stubs(title: String, block: ICorChainDsl<MkplContext>.() -> Unit) = chain {
	block()
	this.title = title
	on { workMode == MkplWorkMode.STUB && state == MkplState.RUNNING }
}

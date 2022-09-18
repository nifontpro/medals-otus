package ru.otus.bussines.stubs

import ru.otus.MkplContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker
import ru.otus.model.MkplCommentId
import ru.otus.model.MkplState
import ru.otus.stubs.CommentStub
import ru.otus.stubs.MkplStubs

fun ICorChainDsl<MkplContext>.stubDeleteSuccess(title: String) = worker {
    this.title = title
    on { stubCase == MkplStubs.SUCCESS && state == MkplState.RUNNING }
    handle {
        state = MkplState.FINISHING
        val stub = CommentStub.prepareResult {
            commentRequest.id.takeIf { it != MkplCommentId.NONE }?.also { this.id = it }
        }
        commentResponse = stub
    }
}

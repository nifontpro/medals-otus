package ru.otus

import ru.otus.model.*
import ru.otus.stubs.MkplStubs
import java.time.Instant

data class MkplContext(

    var command: MkplCommand = MkplCommand.NONE,
    var state: MkplState = MkplState.NONE,
    val errors: MutableList<MkplError> = mutableListOf(),

    var workMode: MkplWorkMode = MkplWorkMode.PROD,
    var stubCase: MkplStubs = MkplStubs.NONE,

    var requestId: MkplRequestId = MkplRequestId.NONE,
    var timeStart: Instant = Instant.MIN,
    var commentRequest: MkplComment = MkplComment(),
    var commentResponse: MkplComment = MkplComment(),
    val commentsResponse: MutableList<MkplComment> = mutableListOf(),
)

package ru.otus.model

@JvmInline
value class MkplCommentId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = MkplCommentId("")
    }
}

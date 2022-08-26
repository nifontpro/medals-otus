package ru.otus.model

@JvmInline
value class MkplRequestId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = MkplRequestId("")
    }
}

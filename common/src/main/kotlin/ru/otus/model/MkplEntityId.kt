package ru.otus.model

@JvmInline
value class MkplEntityId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = MkplEntityId("")
    }
}
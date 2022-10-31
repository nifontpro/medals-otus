package ru.otus.model

import kotlin.jvm.JvmInline

@JvmInline
value class MkplLock(private val id: String) {
	fun asString() = id

	companion object {
		val NONE = MkplLock("")
	}
}

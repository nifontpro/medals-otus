package ru.otus.model

interface IClientSession<T> {
    val fwSession: T
    val apiVersion: String

    companion object {
        val NONE = object : IClientSession<Unit> {
            override val fwSession: Unit = Unit
            override val apiVersion: String = ""
        }
    }
}

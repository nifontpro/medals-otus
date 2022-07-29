package ru.otus.model

import java.util.*

data class User(
    val name: String,
    val email: String,
    val imageUrl: String? = null,

    val id: String
)

@UserDsl
class UserBuilder {
    var name = ""
    var email = ""
    var imageUrl: String? = null

    var id = UUID.randomUUID().toString()

    fun build() = User(name, email, imageUrl, id)
}

@UserDslBuild
fun buildUser(block: UserBuilder.() -> Unit) = UserBuilder().apply(block).build()

@DslMarker
annotation class UserDsl

@DslMarker
annotation class UserDslBuild
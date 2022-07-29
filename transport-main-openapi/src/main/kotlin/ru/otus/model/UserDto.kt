package ru.otus.model

data class UserDto(
    val name: String,
    val email: String,
    val imageUrl: String? = null,

    val id: String
)
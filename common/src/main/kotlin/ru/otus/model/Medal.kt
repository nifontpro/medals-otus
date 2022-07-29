package ru.otus.model

data class Medal(
    val name: String,
    val description: String? = null,
    val userId: String,
    val score: Int = 0,
    val imageUrl: String? = null,

    val id: String
)

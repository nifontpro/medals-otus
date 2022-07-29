package ru.otus.model

data class MedalDto(
    val name: String,
    val description: String? = null,
    val userId: String,
    val score: Int,
    val imageUrl: String? = null,

    val id: String
)

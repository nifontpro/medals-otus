package ru.otus.mappers

import ru.otus.model.Medal
import ru.otus.model.MedalDto

fun Medal.toMedalDto() = MedalDto(
    name = name,
    description = description,
    userId = userId,
    score = score,
    imageUrl = imageUrl,
    id = id
)

fun MedalDto.toMedal() = Medal(
    name = name,
    description = description,
    userId = userId,
    score = score,
    imageUrl = imageUrl,
    id = id
)
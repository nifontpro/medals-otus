package ru.otus.mappers

import ru.otus.model.User
import ru.otus.model.UserDto

fun User.toUserDto() = UserDto(
    name = name,
    email = email,
    imageUrl = imageUrl,
    id = id
)

fun UserDto.toUser() = User(
    name = name,
    email = email,
    imageUrl = imageUrl,
    id = id
)

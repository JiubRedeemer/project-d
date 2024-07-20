package com.jiubredeemer.auth.model.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Запрос для регистрации нового пользователя")
data class UserRegistration(
    @Schema(description = "Имя пользователя", example = "johndoe")
    val username: String,

    @Schema(description = "Email пользователя", example = "user@example.com")
    val email: String,

    @Schema(description = "Пароль пользователя", example = "password123")
    val password: String,

    @Schema(description = "Повторение пароля для валидации", example = "password123")
    val matchingPassword: String
)

package com.jiubredeemer.auth.model.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Запрос для аутентификации по учетным данным")
data class AuthenticationRequest(
    @Schema(description = "Email пользователя", example = "user@example.com")
    val email: String,

    @Schema(description = "Пароль пользователя", example = "password123")
    val password: String,
)

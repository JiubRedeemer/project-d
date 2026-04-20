package com.jiubredeemer.auth.model.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Запрос на смену email по коду подтверждения")
data class ChangeEmailRequest(
    @Schema(description = "Новый email пользователя", example = "new-user@example.com")
    val newEmail: String,

    @Schema(description = "Код из письма", example = "123456")
    val verificationCode: String,

    @Schema(description = "Текущий пароль пользователя", example = "password123")
    val currentPassword: String,
)

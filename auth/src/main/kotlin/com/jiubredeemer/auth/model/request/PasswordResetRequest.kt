package com.jiubredeemer.auth.model.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Запрос на подтверждение кода и установку нового пароля")
data class PasswordResetRequest(
    @Schema(description = "Email аккаунта", example = "user@example.com")
    val email: String,

    @Schema(description = "Код из письма", example = "123456")
    val verificationCode: String,

    @Schema(description = "Новый пароль", example = "new-password-123")
    val password: String,

    @Schema(description = "Повтор нового пароля", example = "new-password-123")
    val matchingPassword: String,
)

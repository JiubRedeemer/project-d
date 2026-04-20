package com.jiubredeemer.auth.model.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Запрос на отправку кода подтверждения для сброса пароля")
data class SendPasswordResetCodeRequest(
    @Schema(description = "Email аккаунта", example = "user@example.com")
    val email: String,
)

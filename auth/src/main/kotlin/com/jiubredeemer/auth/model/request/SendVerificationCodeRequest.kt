package com.jiubredeemer.auth.model.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Запрос на отправку кода подтверждения на email")
data class SendVerificationCodeRequest(
    @Schema(description = "Email для регистрации", example = "user@example.com")
    val email: String,
)

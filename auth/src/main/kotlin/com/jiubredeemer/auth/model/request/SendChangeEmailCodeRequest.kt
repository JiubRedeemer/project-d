package com.jiubredeemer.auth.model.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Запрос на отправку кода подтверждения для смены email")
data class SendChangeEmailCodeRequest(
    @Schema(description = "Новый email пользователя", example = "new-user@example.com")
    val newEmail: String,
)

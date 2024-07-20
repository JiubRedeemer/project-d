package com.jiubredeemer.auth.model.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Запрос для обновления access токена по refresh токену")
data class RefreshTokenRequest(
    @Schema(description = "Refresh токен", example = "dGhpc2lzYXJlZnJlc2h0b2tlbg==")
    val token: String
)

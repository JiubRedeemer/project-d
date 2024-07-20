package com.jiubredeemer.auth.model.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Ответ, содержащий новый токен")
data class TokenResponse(
    @Schema(description = "Новый access токен", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    val token: String
)

package com.jiubredeemer.auth.model.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Ответ на успешную аутентификацию")
data class AuthenticationResponse(
    @Schema(description = "Access токен", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    val accessToken: String,

    @Schema(description = "Refresh токен", example = "dGhpc2lzYXJlZnJlc2h0b2tlbg==")
    val refreshToken: String
)

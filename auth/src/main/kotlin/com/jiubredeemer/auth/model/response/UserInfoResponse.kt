package com.jiubredeemer.auth.model.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Информация о текущем пользователе")
data class UserInfoResponse(
    @Schema(description = "Имя пользователя", example = "johndoe")
    val username: String?,
    @Schema(description = "Email", example = "user@example.com")
    val email: String?,
    @Schema(description = "Дата регистрации", example = "2026-04-20T16:45:12")
    val registrationDate: String?,
    @Schema(description = "Последняя активность", example = "2026-04-20T17:01:05")
    val lastActivity: String?,
)

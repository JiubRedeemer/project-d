package com.jiubredeemer.auth.model.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Ответ с информацией об ошибке")
data class ErrorResponse(
    @Schema(description = "Код ошибки", example = "NOT_FOUND")
    val code: String?,

    @Schema(description = "Сообщение об ошибке", example = "Not Found")
    val message: String?
)

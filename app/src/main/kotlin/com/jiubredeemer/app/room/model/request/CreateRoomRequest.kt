package com.jiubredeemer.app.room.model.request

import com.jiubredeemer.app.integration.dto.RuleTypeEnum
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Запрос на создание комнаты")
data class CreateRoomRequest(
    @Schema(description = "Название комнаты", example = "Днд-шная")
    val name: String? = null,
    @Schema(description = "Описание комнаты", example = "Днд Олега")
    val description: String? = null,
    @Schema(description = "Тип правил", example = "DND5E, DND2024, HOMEBREW")
    val rules: RuleTypeEnum? = null,
    @Schema(description = "Тип базовых правил для хоумбрю", example = "DND5E, DND2024, HOMEBREW")
    var baseRules: RuleTypeEnum? = null,
    @Schema(description = "Ссылка на файл изображения комнаты", example = "Днд Олега")
    val filePath: String? = null,
)

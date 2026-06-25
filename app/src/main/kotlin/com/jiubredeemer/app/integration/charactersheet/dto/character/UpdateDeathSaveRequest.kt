package com.jiubredeemer.app.integration.charactersheet.dto.character

enum class DeathSaveTypeEnum { SUCCESS, FAILURE, RESET }

data class UpdateDeathSaveRequest(
    val type: DeathSaveTypeEnum
)

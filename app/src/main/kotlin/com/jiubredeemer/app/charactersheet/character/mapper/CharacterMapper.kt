package com.jiubredeemer.app.charactersheet.character.mapper

import com.jiubredeemer.app.integration.rulebook.dto.ability.AbilityDto
import com.jiubredeemer.app.rulebook.ability.dto.AbilityResponse
import org.springframework.stereotype.Component

@Component
class CharacterMapper {
    fun mapToResponse(integrationEntities: List<AbilityDto>): List<AbilityResponse> {
        return integrationEntities.map { AbilityResponse(it.name, it.code, it.roomId) }
    }

    fun mapToResponse(integrationEntity: AbilityDto): AbilityResponse {
        return AbilityResponse(integrationEntity.name, integrationEntity.code, integrationEntity.roomId)
    }
}

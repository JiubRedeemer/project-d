package com.jiubredeemer.app.abilities.mapper

import com.jiubredeemer.app.abilities.dto.AbilityResponse
import com.jiubredeemer.app.integration.dto.ability.AbilityDto
import org.springframework.stereotype.Component

@Component
class AbilityMapper {
    fun mapToResponse(integrationEntities: List<AbilityDto>): List<AbilityResponse> {
        return integrationEntities.map { AbilityResponse(it.name, it.code, it.roomId) }
    }

    fun mapToResponse(integrationEntity: AbilityDto): AbilityResponse {
        return AbilityResponse(integrationEntity.name, integrationEntity.code, integrationEntity.roomId)
    }
}

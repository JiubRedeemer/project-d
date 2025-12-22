package com.jiubredeemer.app.charactersheet.character.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.jiubredeemer.app.itemstorage.inventory.dto.common.MultilingualField
import com.jiubredeemer.app.itemstorage.inventory.dto.item.ChargesRefillEnum
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
data class CharacterSkillsDto(
    var id: UUID?,
    var characterId: UUID?,
    var name: String?,
    var description: String?,
    var shortDescription: String?,
    var charges: Int?,
    var currentCharges: Int?,
    var castTime: String?,
    var distance: String?,
    var chargesRefill: ChargesRefillEnum?,
    var imgUrl: String?
)

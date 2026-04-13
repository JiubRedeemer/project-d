package com.jiubredeemer.app.integration.charactersheet.dto.pet

data class PetProfileUpdateRequest(
    var name: String? = null,
    var age: Long? = null,
    var description: String? = null,
    var avatar: String? = null,
    var armorClass: Int? = null,
    var speed: Int? = null,
    var size: String? = null,
    var creatureType: String? = null,
    var proficiencyBonus: Int? = null,
    var senses: String? = null,
    var languages: String? = null,
    var isSummoned: Boolean? = null,
    var isActive: Boolean? = null,
)

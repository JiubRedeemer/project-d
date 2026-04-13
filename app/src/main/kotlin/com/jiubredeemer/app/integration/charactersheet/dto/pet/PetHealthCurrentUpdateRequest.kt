package com.jiubredeemer.app.integration.charactersheet.dto.pet

import com.jiubredeemer.app.charactersheet.pet.dto.PetHealthCurrentUpdateType

data class PetHealthCurrentUpdateRequest(
    var type: PetHealthCurrentUpdateType? = null,
    var value: Long? = null,
)

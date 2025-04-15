package com.jiubredeemer.app.itemstorage.inventory.dto.common

data class DamageObject(
    val value: String?,
    val damageType: DamageTypeEnum?,
    val damageTypeName: String? = damageType?.apiName
)

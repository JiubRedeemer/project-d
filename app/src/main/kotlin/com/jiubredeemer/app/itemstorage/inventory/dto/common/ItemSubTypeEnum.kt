package com.jiubredeemer.app.itemstorage.inventory.dto.common

enum class ItemSubTypeEnum(val apiName: String) {
    SHW("Простое рукопашное"),
    SRW("Простое дальнобойное"),
    AHW("Воинское рукопашное"),
    ARW("Воинское дальнобойное"),
    EHW("Экзотическое рукопашное"),
    ERW("Экзотическое дальнобоайное"),

    HEAVY_ARMOR("Тяжелый доспех"),
    MEDIUM_ARMOR("Средний доспех"),
    LIGHT_ARMOR("Легкий доспех"),
    SHIELD("Щит"),

    NO("")
}

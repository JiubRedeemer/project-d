package com.jiubredeemer.app.websocket

enum class CharacterEventType(val value: String) {
    CHARACTER_UPDATED("character_updated"),
    HEALTH_UPDATED("health_updated"),
    INVENTORY_UPDATED("inventory_updated"),
    SPELLBOOK_UPDATED("spellbook_updated"),
    NOTES_UPDATED("notes_updated"),
}

package com.jiubredeemer.app.websocket

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class PublishCharacterUpdated(
    val eventType: CharacterEventType = CharacterEventType.CHARACTER_UPDATED
)

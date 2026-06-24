package com.jiubredeemer.app.websocket

/**
 * Marks a controller method that modifies character state.
 * After the method returns successfully, a character_updated WebSocket event
 * is published to all subscribers of that character and room.
 *
 * The aspect resolves roomId and characterId from method parameters by name.
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class PublishCharacterUpdated

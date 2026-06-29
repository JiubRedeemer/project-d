package com.jiubredeemer.app.websocket

import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CharacterEventPublisher(
    private val messagingTemplate: SimpMessagingTemplate
) {
    data class CharacterUpdatedEvent(
        val type: String,
        val roomId: UUID,
        val characterId: UUID
    )

    fun publishCharacterUpdated(
        roomId: UUID,
        characterId: UUID,
        eventType: CharacterEventType = CharacterEventType.CHARACTER_UPDATED
    ) {
        val event = CharacterUpdatedEvent(type = eventType.value, roomId = roomId, characterId = characterId)
        messagingTemplate.convertAndSend("/topic/rooms/$roomId/characters/$characterId", event)
        messagingTemplate.convertAndSend("/topic/rooms/$roomId/characters", event)
    }
}

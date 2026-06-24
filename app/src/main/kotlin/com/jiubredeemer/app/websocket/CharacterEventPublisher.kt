package com.jiubredeemer.app.websocket

import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CharacterEventPublisher(
    private val messagingTemplate: SimpMessagingTemplate
) {
    data class CharacterUpdatedEvent(
        val type: String = "character_updated",
        val roomId: UUID,
        val characterId: UUID
    )

    fun publishCharacterUpdated(roomId: UUID, characterId: UUID) {
        val event = CharacterUpdatedEvent(roomId = roomId, characterId = characterId)
        // Notify the specific character's subscribers (player view)
        messagingTemplate.convertAndSend("/topic/rooms/$roomId/characters/$characterId", event)
        // Notify room-level subscribers (master character list)
        messagingTemplate.convertAndSend("/topic/rooms/$roomId/characters", event)
    }
}

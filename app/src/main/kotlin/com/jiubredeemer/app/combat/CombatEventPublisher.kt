package com.jiubredeemer.app.combat

import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import java.util.*

@Service
class CombatEventPublisher(
    private val messagingTemplate: SimpMessagingTemplate
) {
    data class CombatUpdatedEvent(
        val type: String = "combat_updated",
        val roomId: UUID,
        val sessionId: UUID,
    )

    fun publishCombatUpdated(roomId: UUID, sessionId: UUID) {
        val event = CombatUpdatedEvent(roomId = roomId, sessionId = sessionId)
        messagingTemplate.convertAndSend("/topic/rooms/$roomId/combat", event)
    }
}

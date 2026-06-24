package com.jiubredeemer.app.websocket

import com.jiubredeemer.auth.service.TokenService
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.messaging.support.MessageHeaderAccessor
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Component

@Component
class WebSocketAuthInterceptor(
    private val tokenService: TokenService
) : ChannelInterceptor {

    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*> {
        val accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor::class.java)
        if (accessor?.command == StompCommand.CONNECT) {
            val authHeader = accessor.getFirstNativeHeader("Authorization")
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                val token = authHeader.removePrefix("Bearer ")
                try {
                    val email = tokenService.extractEmail(token)
                    if (email != null && !tokenService.isExpired(token)) {
                        accessor.user = UsernamePasswordAuthenticationToken(email, null, emptyList())
                    }
                } catch (_: Exception) {
                    // invalid token — connection proceeds unauthenticated, topics are still readable
                }
            }
        }
        return message
    }
}

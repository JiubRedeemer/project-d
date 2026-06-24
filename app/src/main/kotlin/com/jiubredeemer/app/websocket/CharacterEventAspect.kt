package com.jiubredeemer.app.websocket

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.core.DefaultParameterNameDiscoverer
import org.springframework.stereotype.Component
import java.util.UUID

@Aspect
@Component
class CharacterEventAspect(
    private val characterEventPublisher: CharacterEventPublisher
) {
    private val nameDiscoverer = DefaultParameterNameDiscoverer()

    @AfterReturning("@annotation(com.jiubredeemer.app.websocket.PublishCharacterUpdated)")
    fun afterCharacterMutation(joinPoint: JoinPoint) {
        val method = (joinPoint.signature as MethodSignature).method
        val paramNames = nameDiscoverer.getParameterNames(method) ?: return
        val args = joinPoint.args

        var roomId: UUID? = null
        var characterId: UUID? = null

        paramNames.forEachIndexed { i, name ->
            val value = args.getOrNull(i) as? UUID ?: return@forEachIndexed
            when (name) {
                "roomId" -> roomId = value
                "characterId" -> characterId = value
            }
        }

        if (roomId != null && characterId != null) {
            characterEventPublisher.publishCharacterUpdated(roomId!!, characterId!!)
        }
    }
}

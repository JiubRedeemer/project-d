package com.jiubredeemer.app.integration.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "project-d.integrations.character-sheet")
data class CharacterSheetProperty(
    var baseUrl: String = "",
    var roomsUrl: String = "",
)

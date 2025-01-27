package com.jiubredeemer.app.integration.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "project-d.integrations.character-sheet")
data class CharacterSheetProperty(
    var apiUrl: String = "",
    var baseUrl: String = "",
    var roomsUrl: String = "",
    var charactersUrl: String = "",
    var headerUrl: String = "",
    var subheaderUrl: String = "",
    var abilitiesUrl: String = "",
    var bonusUrl: String = "",
    var healthUrl: String = "",
    var armoryClassUrl: String = "",
    var speedUrl: String = "",
    var initiativeUrl: String = ""
)

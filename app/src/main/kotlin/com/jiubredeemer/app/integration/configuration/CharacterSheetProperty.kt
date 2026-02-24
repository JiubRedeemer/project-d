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
    var bioUrl: String = "",
    var bonusUrl: String = "",
    var healthUrl: String = "",
    var healthMaxUrl: String = "",
    var updateCurrentHealthUrl: String = "",
    var levelUrl: String = "",
    var updateCurrentXpUrl: String = "",
    var levelUpUrl: String = "",
    var levelDownUrl: String = "",
    var armoryClassUrl: String = "",
    var speedUrl: String = "",
    var initiativeUrl: String = "",
    var skillsUrl: String = "",
    var updateMasteryUrl: String = "",
    var characterSkillsUrl: String = "",
    var useUrl: String = "",
    var restUrl: String = ""
)

package com.jiubredeemer.app.integration.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "project-d.integrations.magic")
data class MagicProperty(
    var apiUrl: String = "",
    var baseUrl: String = "",
    var spellsUrl: String = "",
    var spellBooksUrl: String = "",
    var spellBookItemsUrl: String = "",
    var spellCellsUrl: String = "",
    var importUrl: String = "",
    var byRoomCharacterUrl: String = "",
    var inUseUrl: String = ""
)

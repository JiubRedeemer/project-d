package com.jiubredeemer.app.integration.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "project-d.integrations.rule-book")
data class RuleBookProperty(
    var baseUrl: String = "",
    var roomsUrl: String = "",
    var racesUrl: String = "",
    var classesUrl: String = "",
    var abilitiesUrl: String = ""
)

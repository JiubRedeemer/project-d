package com.jiubredeemer.app.integration.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "project-d.integrations.rule-book")
data class RuleBookProperty(
    var apiUrl: String = "",
    var baseUrl: String = "",
    var roomsUrl: String = "",
    var racesUrl: String = "",
    var classesUrl: String = "",
    var raceSpeciesUrl: String = "",
    var classGroupsUrl: String = "",
    var abilitiesUrl: String = "",
    var skillsUrl: String = "",
    var skillsByCodeUrl: String = "",
    var skillsByClassUrl: String = "",
    var backgroundsUrl: String = "",
    var logicalUrl: String = "",
    var rulesUrl: String = ""
)

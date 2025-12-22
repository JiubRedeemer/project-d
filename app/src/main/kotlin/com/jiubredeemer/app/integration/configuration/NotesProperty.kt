package com.jiubredeemer.app.integration.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "project-d.integrations.notes")
data class NotesProperty (
    var apiUrl: String = "",
    var baseUrl: String = "",
    var roomsUrl: String = "",
    var charactersUrl: String = "",
    var notesUrl: String = ""
)
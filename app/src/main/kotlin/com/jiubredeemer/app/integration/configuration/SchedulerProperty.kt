package com.jiubredeemer.app.integration.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "project-d.integrations.scheduler")
data class SchedulerProperty(
    var apiUrl: String = "",
    var gamesUrl: String = "",
    var invitationsUrl: String = "",
    var attendanceUrl: String = "",
    var rescheduleUrl: String = "",
    var cancelUrl: String = "",
    var acceptUrl: String = "",
    var declineUrl: String = "",
    var baseUrl: String = "",
)

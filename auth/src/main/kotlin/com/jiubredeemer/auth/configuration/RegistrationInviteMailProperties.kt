package com.jiubredeemer.auth.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "project-d.invite-mail")
data class RegistrationInviteMailProperties(
    val fromAddress: String = "",
    val registrationBaseUrl: String = "",
)

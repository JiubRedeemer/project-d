package com.jiubredeemer.auth.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "project-d.cors")
data class CorsConfig(
    val allowedUrls: List<String>,
    val allowedMethods: List<String>
)

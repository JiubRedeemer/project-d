package com.jiubredeemer.app.integration.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "project-d.integrations.itemstorage")
data class ItemstorageProperty(
    var apiUrl: String = "",
    var baseUrl: String = "",
    var inventoryUrl: String = "",
    var equipUrl: String = "",
    var countUrl: String = "",
    var moneyUrl: String = "",
)

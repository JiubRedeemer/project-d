package com.jiubredeemer.app.integration.itemstorage

import com.jiubredeemer.app.integration.configuration.ItemstorageProperty
import com.jiubredeemer.app.itemstorage.inventory.dto.inventory.InventoryDto
import com.jiubredeemer.app.itemstorage.inventory.dto.money.MoneyDto
import com.jiubredeemer.common.exception.IntegrationAccessException
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

@Service
class ItemstorageClient(
    private val itemstorageProperty: ItemstorageProperty,
    private val restClient: RestClient
) {
    private val headers = HttpHeaders().apply { set("Content-Type", "application/json") }
    fun findInventoryByCharacterId(characterId: UUID): InventoryDto? {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(itemstorageProperty.baseUrl)
                .pathSegment(itemstorageProperty.apiUrl)
                .pathSegment(itemstorageProperty.inventoryUrl)
                .pathSegment(characterId.toString())
                .toUriString()
            val response = restClient.get()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
                .toEntity(InventoryDto::class.java)
            return response.body
        } catch (e: Exception) {
            throw IntegrationAccessException("Itemstorage don't response on findInventoryByCharacterId, cause: ${e.message}")
        }
    }

    fun equipItemByCharacterIdAndItemId(characterId: UUID, itemId: UUID): InventoryDto? {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(itemstorageProperty.baseUrl)
                .pathSegment(itemstorageProperty.apiUrl)
                .pathSegment(itemstorageProperty.inventoryUrl)
                .pathSegment(characterId.toString())
                .pathSegment(itemstorageProperty.equipUrl)
                .pathSegment(itemId.toString())
                .toUriString()
            val response = restClient.patch()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
                .toEntity(InventoryDto::class.java)
            return response.body
        } catch (e: Exception) {
            throw IntegrationAccessException("Itemstorage don't response on equipItemByCharacterIdAndItemId, cause: ${e.message}")
        }
    }

    fun changeItemCountByCharacterIdAndItemId(characterId: UUID, itemId: UUID, count: Long): InventoryDto? {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(itemstorageProperty.baseUrl)
                .pathSegment(itemstorageProperty.apiUrl)
                .pathSegment(itemstorageProperty.inventoryUrl)
                .pathSegment(characterId.toString())
                .pathSegment(itemId.toString())
                .pathSegment(itemstorageProperty.countUrl)
                .pathSegment(count.toString())
                .toUriString()
            val response = restClient.patch()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
                .toEntity(InventoryDto::class.java)
            return response.body
        } catch (e: Exception) {
            throw IntegrationAccessException("Itemstorage don't response on changeItemCountByCharacterIdAndItemId, cause: ${e.message}")
        }
    }

    fun findMoneyByCharacterId(characterId: UUID): MoneyDto? {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(itemstorageProperty.baseUrl)
                .pathSegment(itemstorageProperty.apiUrl)
                .pathSegment(itemstorageProperty.inventoryUrl)
                .pathSegment(characterId.toString())
                .pathSegment(itemstorageProperty.moneyUrl)
                .toUriString()
            val response = restClient.get()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
                .toEntity(MoneyDto::class.java)
            return response.body
        } catch (e: Exception) {
            throw IntegrationAccessException("Itemstorage don't response on findMoneyByCharacterId, cause: ${e.message}")
        }
    }

    fun changeMoneyCount(characterId: UUID, moneyDto: MoneyDto): MoneyDto? {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(itemstorageProperty.baseUrl)
                .pathSegment(itemstorageProperty.apiUrl)
                .pathSegment(itemstorageProperty.inventoryUrl)
                .pathSegment(characterId.toString())
                .pathSegment(itemstorageProperty.moneyUrl)
                .toUriString()
            val response = restClient.patch()
                .uri(uri)
                .headers { it.addAll(headers) }
                .body(moneyDto)
                .retrieve()
                .toEntity(MoneyDto::class.java)
            return response.body
        } catch (e: Exception) {
            throw IntegrationAccessException("Itemstorage don't response on changeMoneyCount, cause: ${e.message}")
        }
    }
}

package com.jiubredeemer.app.integration.itemstorage

import com.jiubredeemer.app.integration.configuration.ItemstorageProperty
import com.jiubredeemer.app.itemstorage.inventory.dto.inventory.EquippedItemsStatsResponse
import com.jiubredeemer.app.itemstorage.inventory.dto.inventory.InventoryDto
import com.jiubredeemer.app.itemstorage.inventory.dto.inventory.InventoryItemDto
import com.jiubredeemer.app.itemstorage.inventory.dto.item.ItemDto
import com.jiubredeemer.app.itemstorage.inventory.dto.money.MoneyDto
import com.jiubredeemer.common.exception.IntegrationAccessException
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.util.UriComponentsBuilder
import java.time.LocalDateTime
import java.util.*

@Service
class ItemstorageClient(
    private val itemstorageProperty: ItemstorageProperty,
    private val restClient: RestClient
) {
    private val headers = HttpHeaders().apply { set("Content-Type", "application/json") }
    fun findInventoryByCharacterId(roomId: UUID, characterId: UUID): InventoryDto? {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(itemstorageProperty.baseUrl)
                .pathSegment(itemstorageProperty.apiUrl)
                .pathSegment(roomId.toString())
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

    fun equipItemByCharacterIdAndItemId(roomId: UUID, characterId: UUID, itemId: UUID): InventoryDto? {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(itemstorageProperty.baseUrl)
                .pathSegment(itemstorageProperty.apiUrl)
                .pathSegment(roomId.toString())
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

    fun changeItemCountByCharacterIdAndItemId(
        roomId: UUID,
        characterId: UUID,
        itemId: UUID,
        count: Long
    ): InventoryDto? {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(itemstorageProperty.baseUrl)
                .pathSegment(itemstorageProperty.apiUrl)
                .pathSegment(roomId.toString())
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

    fun deleteItemFromInventory(roomId: UUID, characterId: UUID, itemId: UUID): InventoryDto? {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(itemstorageProperty.baseUrl)
                .pathSegment(itemstorageProperty.apiUrl)
                .pathSegment(roomId.toString())
                .pathSegment(itemstorageProperty.inventoryUrl)
                .pathSegment(characterId.toString())
                .pathSegment(itemId.toString())
                .toUriString()
            val response = restClient.delete()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
                .toEntity(InventoryDto::class.java)
            return response.body
        } catch (e: Exception) {
            throw IntegrationAccessException("Itemstorage don't response on deleteItemFromInventory, cause: ${e.message}")
        }
    }

    fun addItemToInventory(roomId: UUID, characterId: UUID, itemId: UUID, count: Long): InventoryDto? {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(itemstorageProperty.baseUrl)
                .pathSegment(itemstorageProperty.apiUrl)
                .pathSegment(roomId.toString())
                .pathSegment(itemstorageProperty.inventoryUrl)
                .pathSegment(characterId.toString())
                .pathSegment(itemId.toString())
                .pathSegment(count.toString())
                .toUriString()
            val response = restClient.put()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
                .toEntity(InventoryDto::class.java)
            return response.body
        } catch (e: Exception) {
            throw IntegrationAccessException("Itemstorage don't response on addItemToInventory, cause: ${e.message}")
        }
    }

    fun findMoneyByCharacterId(roomId: UUID, characterId: UUID): MoneyDto? {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(itemstorageProperty.baseUrl)
                .pathSegment(itemstorageProperty.apiUrl)
                .pathSegment(roomId.toString())
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

    fun changeMoneyCount(roomId: UUID, characterId: UUID, moneyDto: MoneyDto): MoneyDto? {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(itemstorageProperty.baseUrl)
                .pathSegment(itemstorageProperty.apiUrl)
                .pathSegment(roomId.toString())
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

    fun getInventoryItem(roomId: UUID, characterId: UUID, itemId: UUID): InventoryItemDto? {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(itemstorageProperty.baseUrl)
                .pathSegment(itemstorageProperty.apiUrl)
                .pathSegment(roomId.toString())
                .pathSegment(itemstorageProperty.inventoryUrl)
                .pathSegment(characterId.toString())
                .pathSegment(itemstorageProperty.itemsUrl)
                .pathSegment(itemId.toString())
                .toUriString()
            val response = restClient.get()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
                .toEntity(InventoryItemDto::class.java)
            return response.body
        } catch (e: Exception) {
            throw IntegrationAccessException("Itemstorage don't response on getInventoryItem, cause: ${e.message}")
        }
    }

    fun searchByNameRoomAndCommunityItems(
        roomId: UUID,
        userId: UUID,
        searchQuery: String,
        limit: Int,
        lastSeenCreatedAt: LocalDateTime? = null,
        lastSeenId: UUID? = null
    ): List<ItemDto> {
        try {
            val uriBuilder = UriComponentsBuilder
                .fromHttpUrl(itemstorageProperty.baseUrl)
                .pathSegment(itemstorageProperty.apiUrl)
                .pathSegment(itemstorageProperty.itemsUrl)
                .pathSegment(roomId.toString())
                .pathSegment(userId.toString())
                .pathSegment(itemstorageProperty.searchUrl)

            val queryParams = mutableMapOf<String, String>()
            queryParams["searchQuery"] = searchQuery
            queryParams["limit"] = limit.toString()
            lastSeenCreatedAt?.let { queryParams["lastSeenCreatedAt"] = it.toString() }
            lastSeenId?.let { queryParams["lastSeenId"] = it.toString() }

//            queryParams.forEach { (key, value) -> uriBuilder.queryParam(key, value) }

            val uri = uriBuilder.toUriString()

            val response = restClient.post()
                .uri(uri)
                .body(queryParams)
                .headers { it.addAll(headers) }
                .retrieve()
                .toEntity(object : ParameterizedTypeReference<List<ItemDto>>() {})

            return response.body ?: emptyList()
        } catch (e: Exception) {
            throw IntegrationAccessException("Itemstorage didn't respond on searchByNameRoomAndCommunityItems, cause: ${e.message}")
        }
    }

    fun addItem(roomId: UUID, userId: UUID, itemDto: ItemDto): ItemDto {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(itemstorageProperty.baseUrl)
                .pathSegment(itemstorageProperty.apiUrl)
                .pathSegment(itemstorageProperty.itemsUrl)
                .pathSegment(roomId.toString())
                .pathSegment(userId.toString())
                .toUriString()
            val response = restClient.put()
                .uri(uri)
                .headers { it.addAll(headers) }
                .body(itemDto)
                .retrieve()
                .toEntity(ItemDto::class.java)
            return response.body!!
        } catch (e: Exception) {
            throw IntegrationAccessException("Itemstorage didn't respond on addItem, cause: ${e.message}")
        }
    }

    fun getEquippedItemStats(roomId: UUID, characterId: UUID): EquippedItemsStatsResponse {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(itemstorageProperty.baseUrl)
                .pathSegment(itemstorageProperty.apiUrl)
                .pathSegment(itemstorageProperty.bonusUrl)
                .pathSegment(roomId.toString())
                .pathSegment(characterId.toString())
                .toUriString()
            val response = restClient.get()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
                .toEntity(EquippedItemsStatsResponse::class.java)
            return response.body!!
        } catch (e: Exception) {
            throw IntegrationAccessException("Itemstorage didn't respond on getEquippedItemStats, cause: ${e.message}")
        }
    }
}

package com.jiubredeemer.app.itemstorage.inventory.service

import com.jiubredeemer.app.integration.charactersheet.dto.character.BonusValueUpdateRequest
import com.jiubredeemer.app.integration.itemstorage.ItemstorageClient
import com.jiubredeemer.app.itemstorage.inventory.dto.inventory.EquippedItemsStatsResponse
import com.jiubredeemer.app.itemstorage.inventory.dto.inventory.InventoryDto
import com.jiubredeemer.app.itemstorage.inventory.dto.inventory.InventoryItemDto
import com.jiubredeemer.app.itemstorage.inventory.dto.item.ItemDto
import com.jiubredeemer.app.itemstorage.inventory.dto.item.ItemTagDto
import com.jiubredeemer.app.room.service.RoomAccessChecker
import com.jiubredeemer.auth.service.AccessChecker
import com.jiubredeemer.common.exception.NotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class InventoryApiService(
    private val roomAccessChecker: RoomAccessChecker,
    private val accessChecker: AccessChecker,
    private val itemstorageClient: ItemstorageClient,
) {
    fun getInventoryByCharacterId(roomId: UUID, characterId: UUID): InventoryDto {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val inventoryDto: InventoryDto = itemstorageClient.findInventoryByCharacterId(roomId, characterId)
            ?: throw NotFoundException("Inventory not found by character id: $characterId")
        return inventoryDto
    }

    fun equipItemByCharacterIdAndItemId(roomId: UUID, characterId: UUID, itemId: UUID): InventoryDto {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val inventoryDto: InventoryDto = itemstorageClient.equipItemByCharacterIdAndItemId(roomId, characterId, itemId)
            ?: throw NotFoundException("Inventory not found by character id: $characterId")
        return inventoryDto
    }

    fun addBonusAttackToItemById(
        roomId: UUID,
        characterId: UUID,
        itemId: UUID,
        bonusValueUpdateRequest: BonusValueUpdateRequest
    ): InventoryDto {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val inventoryDto: InventoryDto =
            itemstorageClient.addBonusAttackToItemById(roomId, characterId, itemId, bonusValueUpdateRequest)
                ?: throw NotFoundException("Inventory not found by character id: $characterId")
        return inventoryDto
    }

    fun addBonusDamageToItemById(
        roomId: UUID,
        characterId: UUID,
        itemId: UUID,
        bonusValueUpdateRequest: BonusValueUpdateRequest
    ): InventoryDto {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val inventoryDto: InventoryDto =
            itemstorageClient.addBonusDamageToItemById(roomId, characterId, itemId, bonusValueUpdateRequest)
                ?: throw NotFoundException("Inventory not found by character id: $characterId")
        return inventoryDto
    }

    fun changeItemCountByCharacterIdAndItemId(
        roomId: UUID,
        characterId: UUID,
        itemId: UUID,
        count: Long
    ): InventoryDto {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val inventoryDto: InventoryDto =
            itemstorageClient.changeItemCountByCharacterIdAndItemId(roomId, characterId, itemId, count)
                ?: throw NotFoundException("Inventory not found by character id: $characterId")
        return inventoryDto
    }

    fun deleteItemFromInventory(
        roomId: UUID,
        characterId: UUID,
        itemId: UUID,
    ): InventoryDto {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val inventoryDto: InventoryDto = itemstorageClient.deleteItemFromInventory(roomId, characterId, itemId)
            ?: throw NotFoundException("Inventory not found by character id: $characterId")
        return inventoryDto
    }

    fun addItemToInventory(
        roomId: UUID,
        characterId: UUID,
        itemId: UUID,
        count: Long,
    ): InventoryDto {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val inventoryDto: InventoryDto = itemstorageClient.addItemToInventory(roomId, characterId, itemId, count)
            ?: throw NotFoundException("Inventory not found by character id: $characterId")
        return inventoryDto
    }

    fun getInventoryItem(roomId: UUID, characterId: UUID, itemId: UUID): InventoryItemDto {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val inventoryDto: InventoryItemDto = itemstorageClient.getInventoryItem(roomId, characterId, itemId)
            ?: throw NotFoundException("Inventory Item not found by id: $itemId")
        return inventoryDto
    }

    fun searchByNameRoomAndCommunityItems(
        roomId: UUID,
        searchQuery: String,
        limit: Int,
        lastSeenCreatedAt: LocalDateTime? = null,
        lastSeenId: UUID? = null,
        ruleType: String? = null,
        type: String? = null,
        subtype: String? = null,
        rarity: String? = null,
        tags: List<String>? = null,
        customization: Boolean? = null,
        hasSkills: Boolean? = null
    ): List<ItemDto> {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        validateCursorParams(lastSeenCreatedAt, lastSeenId)
        val normalizedSearchQuery = searchQuery.trim()
        validateLimit(limit)
        val items: List<ItemDto> = itemstorageClient.searchByNameRoomAndCommunityItems(
            roomId,
            accessChecker.getCurrentUser().id!!,
            normalizedSearchQuery,
            limit,
            lastSeenCreatedAt,
            lastSeenId,
            ruleType,
            type,
            subtype,
            rarity,
            tags,
            customization,
            hasSkills
        )
        return items
    }

    fun addItem(
        roomId: UUID,
        itemDto: ItemDto
    ): ItemDto {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        itemDto.creator = accessChecker.getCurrentUser().username
        val createdItem: ItemDto = itemstorageClient.addItem(
            roomId,
            accessChecker.getCurrentUser().id!!,
            itemDto
        )
        return createdItem
    }

    fun deleteItem(
        roomId: UUID,
        itemId: UUID
    ) {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        itemstorageClient.deleteItem(
            roomId,
            accessChecker.getCurrentUser().id!!,
            itemId
        )
    }

    fun getEquippedItemStats(
        roomId: UUID,
        characterId: UUID
    ): EquippedItemsStatsResponse {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val response: EquippedItemsStatsResponse = itemstorageClient.getEquippedItemStats(
            roomId,
            characterId
        )
        return response
    }

    fun useInventoryItemSkill(
        roomId: UUID,
        characterId: UUID,
        itemId: UUID,
        skillId: UUID
    ) {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        itemstorageClient.useInventoryItemSkill(roomId, characterId, itemId, skillId)
    }

    fun searchByNameRoomAndCommunityItemsOwnedUsers(
        roomId: UUID,
        searchQuery: String,
        limit: Int,
        lastSeenCreatedAt: LocalDateTime?,
        lastSeenId: UUID?,
        type: String? = null,
        subtype: String? = null,
        rarity: String? = null,
        tags: List<String>? = null,
        customization: Boolean? = null,
        hasSkills: Boolean? = null
    ): List<ItemDto> {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        validateCursorParams(lastSeenCreatedAt, lastSeenId)
        val normalizedSearchQuery = searchQuery.trim()
        validateLimit(limit)
        val items: List<ItemDto> = itemstorageClient.searchByNameRoomAndCommunityItemsOwnedUsers(
            roomId,
            accessChecker.getCurrentUser().id!!,
            normalizedSearchQuery,
            limit,
            lastSeenCreatedAt,
            lastSeenId,
            type,
            subtype,
            rarity,
            tags,
            customization,
            hasSkills
        )
        return items
    }

    fun getDistinctItemTags(roomId: UUID): List<ItemTagDto> {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        return itemstorageClient.getDistinctItemTags(roomId, accessChecker.getCurrentUser().id!!)
    }

    fun createTagForRoom(roomId: UUID, name: String): ItemTagDto {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        return itemstorageClient.createTag(roomId, accessChecker.getCurrentUser().id!!, name)
    }

    fun updateTagDescription(roomId: UUID, tagId: UUID, description: String): ItemTagDto {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        return itemstorageClient.updateTagDescription(roomId, accessChecker.getCurrentUser().id!!, tagId, description)
    }

    private fun validateCursorParams(lastSeenCreatedAt: LocalDateTime?, lastSeenId: UUID?) {
        if ((lastSeenCreatedAt == null) != (lastSeenId == null)) {
            throw IllegalArgumentException("Pagination cursor must include both lastSeenCreatedAt and lastSeenId")
        }
    }

    private fun validateLimit(limit: Int) {
        if (limit <= 0) {
            throw IllegalArgumentException("Pagination limit must be greater than 0")
        }
    }


}

package com.jiubredeemer.app.itemstorage.inventory.service

import com.jiubredeemer.app.integration.itemstorage.ItemstorageClient
import com.jiubredeemer.app.itemstorage.inventory.dto.inventory.EquippedItemsStatsResponse
import com.jiubredeemer.app.itemstorage.inventory.dto.inventory.InventoryDto
import com.jiubredeemer.app.itemstorage.inventory.dto.inventory.InventoryItemDto
import com.jiubredeemer.app.itemstorage.inventory.dto.item.ItemDto
import com.jiubredeemer.app.room.service.RoomAccessChecker
import com.jiubredeemer.auth.service.AccessChecker
import com.jiubredeemer.common.exception.NotFoundException
import com.jiubredeemer.dal.service.UserService
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class InventoryApiService(
    private val roomAccessChecker: RoomAccessChecker,
    private val accessChecker: AccessChecker,
    private val itemstorageClient: ItemstorageClient,
    private val userService: UserService,
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
        lastSeenId: UUID? = null
    ): List<ItemDto> {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val items: List<ItemDto> = itemstorageClient.searchByNameRoomAndCommunityItems(
            roomId,
            accessChecker.getCurrentUser().id!!,
            searchQuery,
            limit,
            lastSeenCreatedAt,
            lastSeenId
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


}

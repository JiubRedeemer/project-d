package com.jiubredeemer.app.itemstorage.inventory.service

import com.jiubredeemer.app.integration.itemstorage.ItemstorageClient
import com.jiubredeemer.app.itemstorage.inventory.dto.inventory.InventoryDto
import com.jiubredeemer.app.itemstorage.inventory.dto.money.MoneyDto
import com.jiubredeemer.app.room.service.RoomAccessChecker
import com.jiubredeemer.auth.service.AccessChecker
import com.jiubredeemer.common.exception.NotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class InventoryApiService(
    private val roomAccessChecker: RoomAccessChecker,
    private val accessChecker: AccessChecker,
    private val itemstorageClient: ItemstorageClient,
) {
    fun getInventoryByCharacterId(roomId: UUID, characterId: UUID): InventoryDto {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val inventoryDto: InventoryDto = itemstorageClient.findInventoryByCharacterId(characterId)
            ?: throw NotFoundException("Inventory not found by character id: $characterId")
        return inventoryDto
    }

    fun equipItemByCharacterIdAndItemId(roomId: UUID, characterId: UUID, itemId: UUID): InventoryDto {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val inventoryDto: InventoryDto = itemstorageClient.equipItemByCharacterIdAndItemId(characterId, itemId)
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
        val inventoryDto: InventoryDto = itemstorageClient.changeItemCountByCharacterIdAndItemId(characterId, itemId, count)
            ?: throw NotFoundException("Inventory not found by character id: $characterId")
        return inventoryDto
    }

    fun changeMoneyCount(
        roomId: UUID,
        characterId: UUID,
        moneyDto: MoneyDto
    ): MoneyDto {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val result: MoneyDto = itemstorageClient.changeMoneyCount(characterId, moneyDto)
            ?: throw NotFoundException("MoneyDto not found by character id: $characterId")
        return result
    }

    fun findMoneyByCharacterId(
        roomId: UUID,
        characterId: UUID,
    ): MoneyDto {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val result: MoneyDto = itemstorageClient.findMoneyByCharacterId(characterId)
            ?: throw NotFoundException("MoneyDto not found by character id: $characterId")
        return result
    }


}

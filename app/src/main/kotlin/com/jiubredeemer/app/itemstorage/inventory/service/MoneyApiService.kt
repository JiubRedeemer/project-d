package com.jiubredeemer.app.itemstorage.inventory.service

import com.jiubredeemer.app.integration.itemstorage.ItemstorageClient
import com.jiubredeemer.app.itemstorage.inventory.dto.money.MoneyDto
import com.jiubredeemer.app.room.service.RoomAccessChecker
import com.jiubredeemer.auth.service.AccessChecker
import com.jiubredeemer.common.exception.NotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class MoneyApiService(
    private val roomAccessChecker: RoomAccessChecker,
    private val accessChecker: AccessChecker,
    private val itemstorageClient: ItemstorageClient,
){
    fun changeMoneyCount(
        roomId: UUID,
        characterId: UUID,
        moneyDto: MoneyDto
    ): MoneyDto {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val result: MoneyDto = itemstorageClient.changeMoneyCount(roomId, characterId, moneyDto)
            ?: throw NotFoundException("MoneyDto not found by character id: $characterId")
        return result
    }

    fun findMoneyByCharacterId(
        roomId: UUID,
        characterId: UUID,
    ): MoneyDto {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val result: MoneyDto = itemstorageClient.findMoneyByCharacterId(roomId, characterId)
            ?: throw NotFoundException("MoneyDto not found by character id: $characterId")
        return result
    }
}

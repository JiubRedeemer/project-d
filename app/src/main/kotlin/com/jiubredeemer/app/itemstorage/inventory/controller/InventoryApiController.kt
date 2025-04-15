package com.jiubredeemer.app.itemstorage.inventory.controller

import com.jiubredeemer.app.itemstorage.inventory.dto.inventory.InventoryDto
import com.jiubredeemer.app.itemstorage.inventory.dto.money.MoneyDto
import com.jiubredeemer.app.itemstorage.inventory.service.InventoryApiService
import com.jiubredeemer.auth.annotation.HasRoleOrThrow
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/rooms")
@Tag(name = "Инвентарь", description = "API для управления инвентарем")
class InventoryApiController(
    private val inventoryApiService: InventoryApiService
) {

    @Operation(summary = "Получить инвентарь персонажа")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Инвентарь персонажа",
                content = [Content(schema = Schema(implementation = InventoryDto::class))]
            ),
            ApiResponse(
                responseCode = "403", description = "Недостаточно прав",
                content = [Content(schema = Schema())]
            )
        ]
    )
    @GetMapping("/{roomId}/inventory/{characterId}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getInventory(@PathVariable roomId: UUID, @PathVariable characterId: UUID): InventoryDto {
        return inventoryApiService.getInventoryByCharacterId(roomId, characterId)
    }

    @Operation(summary = "Экипировать/снять предмет с персонажа")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Инвентарь персонажа",
                content = [Content(schema = Schema(implementation = InventoryDto::class))]
            ),
            ApiResponse(
                responseCode = "403", description = "Недостаточно прав",
                content = [Content(schema = Schema())]
            )
        ]
    )
    @PatchMapping("/{roomId}/inventory/{characterId}/equip/{itemId}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun equipItemByCharacterIdAndItemId(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @PathVariable itemId: UUID
    ): InventoryDto {
        return inventoryApiService.equipItemByCharacterIdAndItemId(roomId, characterId, itemId)
    }

    @Operation(summary = "Экипировать/снять предмет с персонажа")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Инвентарь персонажа",
                content = [Content(schema = Schema(implementation = InventoryDto::class))]
            ),
            ApiResponse(
                responseCode = "403", description = "Недостаточно прав",
                content = [Content(schema = Schema())]
            )
        ]
    )
    @PatchMapping("/{roomId}/inventory/{characterId}/{itemId}/count/{count}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun changeItemCountByCharacterIdAndItemId(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @PathVariable itemId: UUID,
        @PathVariable count: Long
    ): InventoryDto {
        return inventoryApiService.changeItemCountByCharacterIdAndItemId(roomId, characterId, itemId, count)
    }

    @Operation(summary = "Изменить кол-во денег у персонажа")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Деньги персонажа",
                content = [Content(schema = Schema(implementation = InventoryDto::class))]
            ),
            ApiResponse(
                responseCode = "403", description = "Недостаточно прав",
                content = [Content(schema = Schema())]
            )
        ]
    )
    @PatchMapping("/{roomId}/inventory/{characterId}/money")
    @HasRoleOrThrow("ADMIN", "USER")
    fun changeMoneyCount(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @RequestBody moneyDto: MoneyDto
    ): MoneyDto {
        return inventoryApiService.changeMoneyCount(roomId, characterId, moneyDto)
    }

    @Operation(summary = "Получить кол-во денег у персонажа")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Деньги персонажа",
                content = [Content(schema = Schema(implementation = InventoryDto::class))]
            ),
            ApiResponse(
                responseCode = "403", description = "Недостаточно прав",
                content = [Content(schema = Schema())]
            )
        ]
    )
    @GetMapping("/{roomId}/inventory/{characterId}/money")
    @HasRoleOrThrow("ADMIN", "USER")
    fun findMoneyByCharacterId(@PathVariable roomId: UUID, @PathVariable characterId: UUID): MoneyDto {
        return inventoryApiService.findMoneyByCharacterId(roomId, characterId)
    }

}

package com.jiubredeemer.app.itemstorage.inventory.controller

import com.jiubredeemer.app.itemstorage.inventory.dto.inventory.EquippedItemsStatsResponse
import com.jiubredeemer.app.itemstorage.inventory.dto.inventory.InventoryDto
import com.jiubredeemer.app.itemstorage.inventory.dto.inventory.InventoryItemDto
import com.jiubredeemer.app.itemstorage.inventory.dto.item.ItemDto
import com.jiubredeemer.app.itemstorage.inventory.dto.item.SearchItemParams
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
    private val inventoryApiService: InventoryApiService,
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

    @Operation(summary = "Получить предмет инвентаря персонажа")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Предмет инвентаря персонажа",
                content = [Content(schema = Schema(implementation = InventoryItemDto::class))]
            ),
            ApiResponse(
                responseCode = "403", description = "Недостаточно прав",
                content = [Content(schema = Schema())]
            )
        ]
    )
    @GetMapping("/{roomId}/inventory/{characterId}/items/{itemId}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getInventoryItem(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @PathVariable itemId: UUID
    ): InventoryItemDto {
        return inventoryApiService.getInventoryItem(roomId, characterId, itemId)
    }

    @Operation(summary = "Получить предметы из общей базы предметов и из списка комнаты")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Список предметов",
                content = [Content(schema = Schema(implementation = List::class))]
            ),
            ApiResponse(
                responseCode = "403", description = "Недостаточно прав",
                content = [Content(schema = Schema())]
            )
        ]
    )
    @PostMapping("/{roomId}/items/search")
    @HasRoleOrThrow("ADMIN", "USER")
    fun searchByNameRoomAndCommunityItems(
        @PathVariable roomId: UUID,
        @RequestBody searchItemParams: SearchItemParams,
    ): List<ItemDto> {
        return inventoryApiService.searchByNameRoomAndCommunityItems(
            roomId,
            searchItemParams.searchQuery,
            searchItemParams.limit,
            searchItemParams.lastSeenCreatedAt,
            searchItemParams.lastSeenId
        )
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

    @Operation(summary = "Удалить предмет из инвентаря персонажа")
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
    @DeleteMapping("/{roomId}/inventory/{characterId}/{itemId}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun deleteItemFromInventory(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @PathVariable itemId: UUID,
    ): InventoryDto {
        return inventoryApiService.deleteItemFromInventory(roomId, characterId, itemId)
    }

    @Operation(summary = "Добавить предмет в инвентарь персонажа")
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
    @PutMapping("/{roomId}/inventory/{characterId}/{itemId}/{count}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun addItemToInventory(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @PathVariable itemId: UUID,
        @PathVariable count: Long,
    ): InventoryDto {
        return inventoryApiService.addItemToInventory(roomId, characterId, itemId, count)
    }

    @Operation(summary = "Добавить предмет в базу знаний")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Добавленный предмет",
                content = [Content(schema = Schema(implementation = ItemDto::class))]
            ),
            ApiResponse(
                responseCode = "403", description = "Недостаточно прав",
                content = [Content(schema = Schema())]
            )
        ]
    )
    @PutMapping("/{roomId}/items")
    @HasRoleOrThrow("ADMIN", "USER")
    fun addItemToInventory(
        @PathVariable roomId: UUID,
        @RequestBody itemDto: ItemDto
    ): ItemDto {
        return inventoryApiService.addItem(roomId, itemDto)
    }


    @Operation(summary = "Получить список бонусных характеристик надетых на персонажа предметов")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Список бонусов надетых предметов",
                content = [Content(schema = Schema(implementation = ItemDto::class))]
            ),
            ApiResponse(
                responseCode = "403", description = "Недостаточно прав",
                content = [Content(schema = Schema())]
            )
        ]
    )
    @GetMapping("/{roomId}/inventory/{characterId}/bonus")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getEquippedItemStats(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID
    ): EquippedItemsStatsResponse {
        return inventoryApiService.getEquippedItemStats(roomId, characterId)
    }
}

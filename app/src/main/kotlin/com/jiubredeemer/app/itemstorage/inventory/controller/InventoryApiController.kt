package com.jiubredeemer.app.itemstorage.inventory.controller

import com.jiubredeemer.app.integration.charactersheet.dto.character.BonusValueUpdateRequest
import com.jiubredeemer.app.itemstorage.inventory.dto.inventory.EquippedItemsStatsResponse
import com.jiubredeemer.app.itemstorage.inventory.dto.inventory.InventoryDto
import com.jiubredeemer.app.itemstorage.inventory.dto.inventory.InventoryItemDto
import com.jiubredeemer.app.itemstorage.inventory.dto.item.ItemDto
import com.jiubredeemer.app.itemstorage.inventory.dto.item.ItemTagDto
import com.jiubredeemer.app.itemstorage.inventory.dto.item.SearchItemParams
import com.jiubredeemer.app.itemstorage.inventory.service.InventoryApiService
import com.jiubredeemer.app.websocket.CharacterEventType
import com.jiubredeemer.app.websocket.PublishCharacterUpdated
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
            searchItemParams.lastSeenId,
            searchItemParams.ruleType,
            searchItemParams.type,
            searchItemParams.subtype,
            searchItemParams.rarity,
            searchItemParams.tags,
            searchItemParams.customization,
            searchItemParams.hasSkills
        )
    }

    @Operation(summary = "Получить предметы из общей базы предметов и из списка комнаты принадлежащих юзеру")
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
    @PostMapping("/{roomId}/items/search/owned")
    @HasRoleOrThrow("ADMIN", "USER")
    fun searchByNameRoomAndCommunityItemsOwnedUsers(
        @PathVariable roomId: UUID,
        @RequestBody searchItemParams: SearchItemParams,
    ): List<ItemDto> {
        return inventoryApiService.searchByNameRoomAndCommunityItemsOwnedUsers(
            roomId,
            searchItemParams.searchQuery,
            searchItemParams.limit,
            searchItemParams.lastSeenCreatedAt,
            searchItemParams.lastSeenId,
            searchItemParams.type,
            searchItemParams.subtype,
            searchItemParams.rarity,
            searchItemParams.tags,
            searchItemParams.customization,
            searchItemParams.hasSkills
        )
    }

    @GetMapping("/{roomId}/items/tags")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getDistinctItemTags(@PathVariable roomId: UUID): List<ItemTagDto> {
        return inventoryApiService.getDistinctItemTags(roomId)
    }

    @PostMapping("/{roomId}/items/tags")
    @HasRoleOrThrow("ADMIN", "USER")
    fun createTag(
        @PathVariable roomId: UUID,
        @RequestBody body: Map<String, String>
    ): ItemTagDto {
        return inventoryApiService.createTagForRoom(roomId, body["name"] ?: error("name is required"))
    }

    @PatchMapping("/{roomId}/items/tags/{tagId}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun updateTagDescription(
        @PathVariable roomId: UUID,
        @PathVariable tagId: UUID,
        @RequestBody body: Map<String, String>
    ): ItemTagDto {
        return inventoryApiService.updateTagDescription(roomId, tagId, body["description"] ?: "")
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
    @PublishCharacterUpdated(CharacterEventType.INVENTORY_UPDATED)
    fun equipItemByCharacterIdAndItemId(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @PathVariable itemId: UUID
    ): InventoryDto {
        return inventoryApiService.equipItemByCharacterIdAndItemId(roomId, characterId, itemId)
    }

    @Operation(summary = "Изменить бонусное значение атаки у предмета")
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
    @PatchMapping("/{roomId}/inventory/{characterId}/items/{itemId}/attack/bonus")
    @HasRoleOrThrow("ADMIN", "USER")
    @PublishCharacterUpdated(CharacterEventType.INVENTORY_UPDATED)
    fun addBonusAttackToItemById(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @PathVariable itemId: UUID,
        @RequestBody bonusValueUpdateRequest: BonusValueUpdateRequest
    ): InventoryDto {
        return inventoryApiService.addBonusAttackToItemById(roomId, characterId, itemId, bonusValueUpdateRequest)
    }

    @Operation(summary = "Изменить бонусное значение урона у предмета")
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
    @PatchMapping("/{roomId}/inventory/{characterId}/items/{itemId}/damage/bonus")
    @HasRoleOrThrow("ADMIN", "USER")
    @PublishCharacterUpdated(CharacterEventType.INVENTORY_UPDATED)
    fun addBonusDamageToItemById(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @PathVariable itemId: UUID,
        @RequestBody bonusValueUpdateRequest: BonusValueUpdateRequest
    ): InventoryDto {
        return inventoryApiService.addBonusDamageToItemById(roomId, characterId, itemId, bonusValueUpdateRequest)
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
    @PublishCharacterUpdated(CharacterEventType.INVENTORY_UPDATED)
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
    @PublishCharacterUpdated(CharacterEventType.INVENTORY_UPDATED)
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
    @PublishCharacterUpdated(CharacterEventType.INVENTORY_UPDATED)
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

    @Operation(summary = "Удалить предмет из базы знаний")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Удален предмет",
            ),
            ApiResponse(
                responseCode = "403", description = "Недостаточно прав",
                content = [Content(schema = Schema())]
            )
        ]
    )
    @DeleteMapping("/{roomId}/items/{itemId}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun deleteItem(
        @PathVariable roomId: UUID,
        @PathVariable itemId: UUID
    ) {
        return inventoryApiService.deleteItem(roomId, itemId)
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
    @PostMapping("/{roomId}/inventory/{characterId}/items/{itemId}/skills/{skillId}/use")
    @HasRoleOrThrow("ADMIN", "USER")
    @PublishCharacterUpdated(CharacterEventType.INVENTORY_UPDATED)
    fun useInventoryItemSkill(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @PathVariable itemId: UUID,
        @PathVariable skillId: UUID
    ) {
        inventoryApiService.useInventoryItemSkill(roomId, characterId, itemId, skillId)
    }
}

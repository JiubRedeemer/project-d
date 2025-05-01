package com.jiubredeemer.app.itemstorage.inventory.controller

import com.jiubredeemer.app.itemstorage.inventory.dto.inventory.InventoryDto
import com.jiubredeemer.app.itemstorage.inventory.dto.money.MoneyDto
import com.jiubredeemer.app.itemstorage.inventory.service.MoneyApiService
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
@Tag(name = "Кошель", description = "API для управления кошельком персонажа")
class MoneyApiController(
    private val moneyApiService: MoneyApiService
) {
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
        return moneyApiService.changeMoneyCount(roomId, characterId, moneyDto)
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
        return moneyApiService.findMoneyByCharacterId(roomId, characterId)
    }
}

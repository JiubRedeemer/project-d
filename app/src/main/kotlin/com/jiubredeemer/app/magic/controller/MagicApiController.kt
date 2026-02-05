package com.jiubredeemer.app.magic.controller

import com.jiubredeemer.app.integration.magic.dto.ChargesRefillEnum
import com.jiubredeemer.app.integration.magic.dto.ImportResult
import com.jiubredeemer.app.integration.magic.dto.RefillRestRequest
import com.jiubredeemer.app.integration.magic.dto.SpellBookDto
import com.jiubredeemer.app.integration.magic.dto.SpellBookItemDto
import com.jiubredeemer.app.integration.magic.dto.SpellCellDto
import com.jiubredeemer.app.integration.magic.dto.SpellClass
import com.jiubredeemer.app.integration.magic.dto.SpellDto
import com.jiubredeemer.app.magic.service.MagicApiService
import com.jiubredeemer.auth.annotation.HasRoleOrThrow
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api")
@Tag(name = "Magic", description = "API for spell books, spells, spell cells, and spell import")
class MagicApiController(
    private val magicApiService: MagicApiService,
) {

    // Spells
    @Operation(summary = "List all spells (optionally filter by class)")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "List of spells", content = [Content(schema = Schema(implementation = SpellDto::class))]),
            ApiResponse(responseCode = "403", description = "Forbidden", content = [Content(schema = Schema())]),
        ]
    )
    @GetMapping("/spells")
    @HasRoleOrThrow("ADMIN", "USER")
    fun listSpells(@RequestParam(required = false) spellClass: SpellClass?): List<SpellDto> =
        magicApiService.listSpells(spellClass?.name)

    @Operation(summary = "Create a spell")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Created spell", content = [Content(schema = Schema(implementation = SpellDto::class))]),
            ApiResponse(responseCode = "403", description = "Forbidden", content = [Content(schema = Schema())]),
        ]
    )
    @PostMapping("/spells")
    @HasRoleOrThrow("ADMIN", "USER")
    fun createSpell(@RequestBody spellDto: SpellDto): SpellDto =
        magicApiService.createSpell(spellDto)

    @Operation(summary = "Import spells from TTG")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Import result", content = [Content(schema = Schema(implementation = ImportResult::class))]),
            ApiResponse(responseCode = "403", description = "Forbidden", content = [Content(schema = Schema())]),
        ]
    )
    @PostMapping("/spells/import")
    @HasRoleOrThrow("ADMIN")
    fun importSpells(): ImportResult =
        magicApiService.importSpells()

    @Operation(summary = "Get spell by ID")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Spell", content = [Content(schema = Schema(implementation = SpellDto::class))]),
            ApiResponse(responseCode = "403", description = "Forbidden", content = [Content(schema = Schema())]),
        ]
    )
    @GetMapping("/spells/{id}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getSpellById(@PathVariable id: UUID): SpellDto =
        magicApiService.getSpellById(id)

    @Operation(summary = "Update a spell")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Updated spell", content = [Content(schema = Schema(implementation = SpellDto::class))]),
            ApiResponse(responseCode = "403", description = "Forbidden", content = [Content(schema = Schema())]),
        ]
    )
    @PutMapping("/spells/{id}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun updateSpell(@PathVariable id: UUID, @RequestBody spellDto: SpellDto): SpellDto =
        magicApiService.updateSpell(id, spellDto)

    @Operation(summary = "Delete a spell")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "No content"),
            ApiResponse(responseCode = "403", description = "Forbidden", content = [Content(schema = Schema())]),
        ]
    )
    @DeleteMapping("/spells/{id}")
    @HasRoleOrThrow("ADMIN", "USER")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteSpell(@PathVariable id: UUID) {
        magicApiService.deleteSpell(id)
    }

    // Spell Books
    @Operation(summary = "List all spell books")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "List of spell books", content = [Content(schema = Schema(implementation = SpellBookDto::class))]),
            ApiResponse(responseCode = "403", description = "Forbidden", content = [Content(schema = Schema())]),
        ]
    )
    @GetMapping("/spell-books")
    @HasRoleOrThrow("ADMIN", "USER")
    fun listSpellBooks(): List<SpellBookDto> =
        magicApiService.listSpellBooks()

    @Operation(summary = "Create a spell book")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Created spell book", content = [Content(schema = Schema(implementation = SpellBookDto::class))]),
            ApiResponse(responseCode = "403", description = "Forbidden", content = [Content(schema = Schema())]),
        ]
    )
    @PostMapping("/spell-books")
    @HasRoleOrThrow("ADMIN", "USER")
    fun createSpellBook(@RequestBody spellBookDto: SpellBookDto): SpellBookDto =
        magicApiService.createSpellBook(spellBookDto)

    @Operation(summary = "Get spell book by room and character")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Spell book", content = [Content(schema = Schema(implementation = SpellBookDto::class))]),
            ApiResponse(responseCode = "403", description = "Forbidden", content = [Content(schema = Schema())]),
        ]
    )
    @GetMapping("/spell-books/by-room-character")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getSpellBookByRoomAndCharacter(
        @RequestParam roomId: UUID,
        @RequestParam characterId: UUID,
    ): SpellBookDto =
        magicApiService.getSpellBookByRoomAndCharacter(roomId, characterId)

    @Operation(summary = "Get spell book by ID")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Spell book", content = [Content(schema = Schema(implementation = SpellBookDto::class))]),
            ApiResponse(responseCode = "403", description = "Forbidden", content = [Content(schema = Schema())]),
        ]
    )
    @GetMapping("/spell-books/{spellBookId}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getSpellBookById(@PathVariable spellBookId: UUID): SpellBookDto =
        magicApiService.getSpellBookById(spellBookId)

    @Operation(summary = "Update a spell book")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Updated spell book", content = [Content(schema = Schema(implementation = SpellBookDto::class))]),
            ApiResponse(responseCode = "403", description = "Forbidden", content = [Content(schema = Schema())]),
        ]
    )
    @PutMapping("/spell-books/{spellBookId}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun updateSpellBook(
        @PathVariable spellBookId: UUID,
        @RequestBody spellBookDto: SpellBookDto,
    ): SpellBookDto =
        magicApiService.updateSpellBook(spellBookId, spellBookDto)

    @Operation(summary = "Delete a spell book")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "No content"),
            ApiResponse(responseCode = "403", description = "Forbidden", content = [Content(schema = Schema())]),
        ]
    )
    @DeleteMapping("/spell-books/{spellBookId}")
    @HasRoleOrThrow("ADMIN", "USER")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteSpellBook(@PathVariable spellBookId: UUID) {
        magicApiService.deleteSpellBook(spellBookId)
    }

    @Operation(summary = "Add spell to spell book")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Updated spell book", content = [Content(schema = Schema(implementation = SpellBookDto::class))]),
            ApiResponse(responseCode = "403", description = "Forbidden", content = [Content(schema = Schema())]),
        ]
    )
    @PostMapping("/spell-books/{spellBookId}/spells/{spellId}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun addSpellToBook(
        @PathVariable spellBookId: UUID,
        @PathVariable spellId: UUID,
    ): SpellBookDto =
        magicApiService.addSpellToBook(spellBookId, spellId)

    @Operation(summary = "Remove spell from spell book")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Updated spell book", content = [Content(schema = Schema(implementation = SpellBookDto::class))]),
            ApiResponse(responseCode = "403", description = "Forbidden", content = [Content(schema = Schema())]),
        ]
    )
    @DeleteMapping("/spell-books/{spellBookId}/spells/{spellId}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun removeSpellFromBook(
        @PathVariable spellBookId: UUID,
        @PathVariable spellId: UUID,
    ): SpellBookDto =
        magicApiService.removeSpellFromBook(spellBookId, spellId)

    @Operation(summary = "Set spell in-use flag in spell book")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Updated spell book item", content = [Content(schema = Schema(implementation = SpellBookItemDto::class))]),
            ApiResponse(responseCode = "403", description = "Forbidden", content = [Content(schema = Schema())]),
        ]
    )
    @PatchMapping("/spell-books/{spellBookId}/spells/{spellId}/in-use")
    @HasRoleOrThrow("ADMIN", "USER")
    fun setSpellInUse(
        @PathVariable spellBookId: UUID,
        @PathVariable spellId: UUID,
        @RequestParam inUse: Boolean,
    ): SpellBookItemDto =
        magicApiService.setSpellInUse(spellBookId, spellId, inUse)

    @Operation(summary = "Create a new spell cell for the spell book")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Created spell cell", content = [Content(schema = Schema(implementation = SpellCellDto::class))]),
            ApiResponse(responseCode = "403", description = "Forbidden", content = [Content(schema = Schema())]),
        ]
    )
    @PostMapping("/spell-books/{spellBookId}/spell-cells")
    @HasRoleOrThrow("ADMIN", "USER")
    fun createSpellCellForBook(
        @PathVariable spellBookId: UUID,
        @RequestBody spellCellDto: SpellCellDto,
    ): SpellCellDto =
        magicApiService.createSpellCellForBook(spellBookId, spellCellDto)

    @Operation(summary = "Refill spell cells by rest (sets currentCount = maxCount for cells matching rest type)")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Updated spell book with refilled cells", content = [Content(schema = Schema(implementation = SpellBookDto::class))]),
            ApiResponse(responseCode = "403", description = "Forbidden", content = [Content(schema = Schema())]),
        ]
    )
    @PostMapping("/spell-books/{spellBookId}/rest")
    @HasRoleOrThrow("ADMIN", "USER")
    fun refillRest(
        @PathVariable spellBookId: UUID,
        @RequestBody request: RefillRestRequest,
    ): SpellBookDto =
        magicApiService.refillRest(spellBookId, request)

    @Operation(summary = "Refill spell cells of character spell book by rest (room + character)")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Updated spell book with refilled cells", content = [Content(schema = Schema(implementation = SpellBookDto::class))]),
            ApiResponse(responseCode = "403", description = "Forbidden", content = [Content(schema = Schema())]),
        ]
    )
    @PostMapping("/spell-books/by-room-character/rest")
    @HasRoleOrThrow("ADMIN", "USER")
    fun refillRestByCharacter(
        @RequestParam roomId: UUID,
        @RequestParam characterId: UUID,
        @RequestParam restType: ChargesRefillEnum,
    ): SpellBookDto =
        magicApiService.refillRestByCharacter(roomId, characterId, restType.name)

    // Spell Book Items
    @Operation(summary = "List all spell book items")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "List of spell book items", content = [Content(schema = Schema(implementation = SpellBookItemDto::class))]),
            ApiResponse(responseCode = "403", description = "Forbidden", content = [Content(schema = Schema())]),
        ]
    )
    @GetMapping("/spell-book-items")
    @HasRoleOrThrow("ADMIN", "USER")
    fun listSpellBookItems(): List<SpellBookItemDto> =
        magicApiService.listSpellBookItems()

    @Operation(summary = "Create a spell book item")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Created spell book item", content = [Content(schema = Schema(implementation = SpellBookItemDto::class))]),
            ApiResponse(responseCode = "403", description = "Forbidden", content = [Content(schema = Schema())]),
        ]
    )
    @PostMapping("/spell-book-items")
    @HasRoleOrThrow("ADMIN", "USER")
    fun createSpellBookItem(@RequestBody spellBookItemDto: SpellBookItemDto): SpellBookItemDto =
        magicApiService.createSpellBookItem(spellBookItemDto)

    @Operation(summary = "Get spell book item by ID")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Spell book item", content = [Content(schema = Schema(implementation = SpellBookItemDto::class))]),
            ApiResponse(responseCode = "403", description = "Forbidden", content = [Content(schema = Schema())]),
        ]
    )
    @GetMapping("/spell-book-items/{id}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getSpellBookItemById(@PathVariable id: UUID): SpellBookItemDto =
        magicApiService.getSpellBookItemById(id)

    @Operation(summary = "Update a spell book item")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Updated spell book item", content = [Content(schema = Schema(implementation = SpellBookItemDto::class))]),
            ApiResponse(responseCode = "403", description = "Forbidden", content = [Content(schema = Schema())]),
        ]
    )
    @PutMapping("/spell-book-items/{id}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun updateSpellBookItem(
        @PathVariable id: UUID,
        @RequestBody spellBookItemDto: SpellBookItemDto,
    ): SpellBookItemDto =
        magicApiService.updateSpellBookItem(id, spellBookItemDto)

    @Operation(summary = "Delete a spell book item")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "No content"),
            ApiResponse(responseCode = "403", description = "Forbidden", content = [Content(schema = Schema())]),
        ]
    )
    @DeleteMapping("/spell-book-items/{id}")
    @HasRoleOrThrow("ADMIN", "USER")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteSpellBookItem(@PathVariable id: UUID) {
        magicApiService.deleteSpellBookItem(id)
    }

    @Operation(summary = "Set spell book item in-use flag")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Updated spell book item", content = [Content(schema = Schema(implementation = SpellBookItemDto::class))]),
            ApiResponse(responseCode = "403", description = "Forbidden", content = [Content(schema = Schema())]),
        ]
    )
    @PatchMapping("/spell-book-items/{id}/in-use")
    @HasRoleOrThrow("ADMIN", "USER")
    fun setSpellBookItemInUse(
        @PathVariable id: UUID,
        @RequestParam inUse: Boolean,
    ): SpellBookItemDto =
        magicApiService.setSpellBookItemInUse(id, inUse)

    // Spell Cells
    @Operation(summary = "List all spell cells")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "List of spell cells", content = [Content(schema = Schema(implementation = SpellCellDto::class))]),
            ApiResponse(responseCode = "403", description = "Forbidden", content = [Content(schema = Schema())]),
        ]
    )
    @GetMapping("/spell-cells")
    @HasRoleOrThrow("ADMIN", "USER")
    fun listSpellCells(): List<SpellCellDto> =
        magicApiService.listSpellCells()

    @Operation(summary = "Create a spell cell")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Created spell cell", content = [Content(schema = Schema(implementation = SpellCellDto::class))]),
            ApiResponse(responseCode = "403", description = "Forbidden", content = [Content(schema = Schema())]),
        ]
    )
    @PostMapping("/spell-cells")
    @HasRoleOrThrow("ADMIN", "USER")
    fun createSpellCell(@RequestBody spellCellDto: SpellCellDto): SpellCellDto =
        magicApiService.createSpellCell(spellCellDto)

    @Operation(summary = "Get spell cell by ID")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Spell cell", content = [Content(schema = Schema(implementation = SpellCellDto::class))]),
            ApiResponse(responseCode = "403", description = "Forbidden", content = [Content(schema = Schema())]),
        ]
    )
    @GetMapping("/spell-cells/{id}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getSpellCellById(@PathVariable id: UUID): SpellCellDto =
        magicApiService.getSpellCellById(id)

    @Operation(summary = "Update a spell cell (e.g. maxCount / currentCount)")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Updated spell cell", content = [Content(schema = Schema(implementation = SpellCellDto::class))]),
            ApiResponse(responseCode = "403", description = "Forbidden", content = [Content(schema = Schema())]),
        ]
    )
    @PutMapping("/spell-cells/{id}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun updateSpellCell(
        @PathVariable id: UUID,
        @RequestBody spellCellDto: SpellCellDto,
    ): SpellCellDto =
        magicApiService.updateSpellCell(id, spellCellDto)

    @Operation(summary = "Use one charge from spell cell (currentCount - 1)")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Updated spell cell", content = [Content(schema = Schema(implementation = SpellCellDto::class))]),
            ApiResponse(responseCode = "400", description = "Spell cell has no charges left"),
            ApiResponse(responseCode = "403", description = "Forbidden", content = [Content(schema = Schema())]),
        ]
    )
    @PostMapping("/spell-cells/{id}/use")
    @HasRoleOrThrow("ADMIN", "USER")
    fun useSpellCell(@PathVariable id: UUID): SpellCellDto =
        magicApiService.useSpellCell(id)

    @Operation(summary = "Delete a spell cell")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "No content"),
            ApiResponse(responseCode = "403", description = "Forbidden", content = [Content(schema = Schema())]),
        ]
    )
    @DeleteMapping("/spell-cells/{id}")
    @HasRoleOrThrow("ADMIN", "USER")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteSpellCell(@PathVariable id: UUID) {
        magicApiService.deleteSpellCell(id)
    }
}

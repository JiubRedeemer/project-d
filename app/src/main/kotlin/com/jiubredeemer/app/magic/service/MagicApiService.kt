package com.jiubredeemer.app.magic.service

import com.jiubredeemer.app.integration.magic.MagicClient
import com.jiubredeemer.app.integration.magic.dto.ImportResult
import com.jiubredeemer.app.integration.magic.dto.RefillRestRequest
import com.jiubredeemer.app.integration.magic.dto.SpellBookDto
import com.jiubredeemer.app.integration.magic.dto.SpellBookItemDto
import com.jiubredeemer.app.integration.magic.dto.SpellCellDto
import com.jiubredeemer.app.integration.magic.dto.SpellDto
import com.jiubredeemer.app.room.service.RoomAccessChecker
import com.jiubredeemer.auth.service.AccessChecker
import com.jiubredeemer.common.exception.NotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class MagicApiService(
    private val roomAccessChecker: RoomAccessChecker,
    private val accessChecker: AccessChecker,
    private val magicClient: MagicClient,
) {

    private fun ensureAccessToRoom(roomId: UUID) {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
    }

    private fun ensureAccessToSpellBook(spellBookId: UUID) {
        val book = magicClient.getSpellBookById(spellBookId)
            ?: throw NotFoundException("Spell book not found by id: $spellBookId")
        val roomId = book.roomId ?: throw NotFoundException("Spell book has no roomId")
        ensureAccessToRoom(roomId)
    }

    // Spells (no room in spec; role-only access)
    fun listSpells(spellClass: String?): List<SpellDto> {
        return magicClient.listSpells(spellClass) ?: emptyList()
    }

    fun createSpell(spellDto: SpellDto): SpellDto {
        return magicClient.createSpell(spellDto) ?: throw NotFoundException("Failed to create spell")
    }

    fun importSpells(): ImportResult {
        return magicClient.importSpells() ?: throw NotFoundException("Failed to import spells")
    }

    fun getSpellById(id: UUID): SpellDto {
        return magicClient.getSpellById(id) ?: throw NotFoundException("Spell not found by id: $id")
    }

    fun updateSpell(id: UUID, spellDto: SpellDto): SpellDto {
        return magicClient.updateSpell(id, spellDto) ?: throw NotFoundException("Spell not found by id: $id")
    }

    fun deleteSpell(id: UUID) {
        magicClient.deleteSpell(id)
    }

    // Spell Books
    fun listSpellBooks(): List<SpellBookDto> {
        return magicClient.listSpellBooks() ?: emptyList()
    }

    fun createSpellBook(spellBookDto: SpellBookDto): SpellBookDto {
        spellBookDto.roomId?.let { ensureAccessToRoom(it) }
        return magicClient.createSpellBook(spellBookDto) ?: throw NotFoundException("Failed to create spell book")
    }

    fun getSpellBookByRoomAndCharacter(roomId: UUID, characterId: UUID): SpellBookDto {
        ensureAccessToRoom(roomId)
        return magicClient.getSpellBookByRoomAndCharacter(roomId, characterId)
            ?: throw NotFoundException("Spell book not found for room: $roomId, character: $characterId")
    }

    fun getSpellBookById(spellBookId: UUID): SpellBookDto {
        ensureAccessToSpellBook(spellBookId)
        return magicClient.getSpellBookById(spellBookId)
            ?: throw NotFoundException("Spell book not found by id: $spellBookId")
    }

    fun updateSpellBook(spellBookId: UUID, spellBookDto: SpellBookDto): SpellBookDto {
        ensureAccessToSpellBook(spellBookId)
        return magicClient.updateSpellBook(spellBookId, spellBookDto)
            ?: throw NotFoundException("Spell book not found by id: $spellBookId")
    }

    fun deleteSpellBook(spellBookId: UUID) {
        ensureAccessToSpellBook(spellBookId)
        magicClient.deleteSpellBook(spellBookId)
    }

    fun addSpellToBook(spellBookId: UUID, spellId: UUID): SpellBookDto {
        ensureAccessToSpellBook(spellBookId)
        return magicClient.addSpellToBook(spellBookId, spellId)
            ?: throw NotFoundException("Failed to add spell to spell book")
    }

    fun removeSpellFromBook(spellBookId: UUID, spellId: UUID): SpellBookDto {
        ensureAccessToSpellBook(spellBookId)
        return magicClient.removeSpellFromBook(spellBookId, spellId)
            ?: throw NotFoundException("Failed to remove spell from spell book")
    }

    fun setSpellInUse(spellBookId: UUID, spellId: UUID, inUse: Boolean): SpellBookItemDto {
        ensureAccessToSpellBook(spellBookId)
        return magicClient.setSpellInUse(spellBookId, spellId, inUse)
            ?: throw NotFoundException("Failed to set spell in-use")
    }

    fun createSpellCellForBook(spellBookId: UUID, spellCellDto: SpellCellDto): SpellCellDto {
        ensureAccessToSpellBook(spellBookId)
        return magicClient.createSpellCellForBook(spellBookId, spellCellDto)
            ?: throw NotFoundException("Failed to create spell cell for book")
    }

    fun refillRest(spellBookId: UUID, request: RefillRestRequest): SpellBookDto {
        ensureAccessToSpellBook(spellBookId)
        return magicClient.refillRest(spellBookId, request)
            ?: throw NotFoundException("Failed to refill rest")
    }

    fun refillRestByCharacter(roomId: UUID, characterId: UUID, restType: String): SpellBookDto {
        ensureAccessToRoom(roomId)
        return magicClient.refillRestByCharacter(roomId, characterId, restType)
            ?: throw NotFoundException("Failed to refill rest by character")
    }

    // Spell Book Items
    fun listSpellBookItems(): List<SpellBookItemDto> {
        return magicClient.listSpellBookItems() ?: emptyList()
    }

    fun createSpellBookItem(spellBookItemDto: SpellBookItemDto): SpellBookItemDto {
        spellBookItemDto.spellBookId?.let { ensureAccessToSpellBook(it) }
        return magicClient.createSpellBookItem(spellBookItemDto)
            ?: throw NotFoundException("Failed to create spell book item")
    }

    fun getSpellBookItemById(id: UUID): SpellBookItemDto {
        val item = magicClient.getSpellBookItemById(id)
            ?: throw NotFoundException("Spell book item not found by id: $id")
        item.spellBookId?.let { ensureAccessToSpellBook(it) }
        return item
    }

    fun updateSpellBookItem(id: UUID, spellBookItemDto: SpellBookItemDto): SpellBookItemDto {
        val existing = magicClient.getSpellBookItemById(id)
            ?: throw NotFoundException("Spell book item not found by id: $id")
        existing.spellBookId?.let { ensureAccessToSpellBook(it) }
        spellBookItemDto.spellBookId?.let { ensureAccessToSpellBook(it) }
        return magicClient.updateSpellBookItem(id, spellBookItemDto)
            ?: throw NotFoundException("Spell book item not found by id: $id")
    }

    fun deleteSpellBookItem(id: UUID) {
        val item = magicClient.getSpellBookItemById(id)
            ?: throw NotFoundException("Spell book item not found by id: $id")
        item.spellBookId?.let { ensureAccessToSpellBook(it) }
        magicClient.deleteSpellBookItem(id)
    }

    fun setSpellBookItemInUse(id: UUID, inUse: Boolean): SpellBookItemDto {
        val item = magicClient.getSpellBookItemById(id)
            ?: throw NotFoundException("Spell book item not found by id: $id")
        item.spellBookId?.let { ensureAccessToSpellBook(it) }
        return magicClient.setSpellBookItemInUse(id, inUse)
            ?: throw NotFoundException("Spell book item not found by id: $id")
    }

    // Spell Cells
    fun listSpellCells(): List<SpellCellDto> {
        return magicClient.listSpellCells() ?: emptyList()
    }

    fun createSpellCell(spellCellDto: SpellCellDto): SpellCellDto {
        spellCellDto.spellBookId?.let { ensureAccessToSpellBook(it) }
        return magicClient.createSpellCell(spellCellDto)
            ?: throw NotFoundException("Failed to create spell cell")
    }

    fun getSpellCellById(id: UUID): SpellCellDto {
        val cell = magicClient.getSpellCellById(id)
            ?: throw NotFoundException("Spell cell not found by id: $id")
        cell.spellBookId?.let { ensureAccessToSpellBook(it) }
        return cell
    }

    fun updateSpellCell(id: UUID, spellCellDto: SpellCellDto): SpellCellDto {
        val existing = magicClient.getSpellCellById(id)
            ?: throw NotFoundException("Spell cell not found by id: $id")
        existing.spellBookId?.let { ensureAccessToSpellBook(it) }
        spellCellDto.spellBookId?.let { ensureAccessToSpellBook(it) }
        return magicClient.updateSpellCell(id, spellCellDto)
            ?: throw NotFoundException("Spell cell not found by id: $id")
    }

    fun deleteSpellCell(id: UUID) {
        val cell = magicClient.getSpellCellById(id)
            ?: throw NotFoundException("Spell cell not found by id: $id")
        cell.spellBookId?.let { ensureAccessToSpellBook(it) }
        magicClient.deleteSpellCell(id)
    }

    fun useSpellCell(id: UUID): SpellCellDto {
        val cell = magicClient.getSpellCellById(id)
            ?: throw NotFoundException("Spell cell not found by id: $id")
        cell.spellBookId?.let { ensureAccessToSpellBook(it) }
        return magicClient.useSpellCell(id)
            ?: throw NotFoundException("Failed to use spell cell")
    }
}

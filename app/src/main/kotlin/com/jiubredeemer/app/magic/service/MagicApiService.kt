package com.jiubredeemer.app.magic.service

import com.jiubredeemer.app.integration.magic.MagicClient
import com.jiubredeemer.app.integration.magic.dto.*
import com.jiubredeemer.app.integration.rulebook.RuleBookClient
import com.jiubredeemer.app.room.service.RoomAccessChecker
import com.jiubredeemer.app.websocket.CharacterEventPublisher
import com.jiubredeemer.app.websocket.CharacterEventType
import com.jiubredeemer.auth.service.AccessChecker
import com.jiubredeemer.common.exception.NotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class MagicApiService(
    private val roomAccessChecker: RoomAccessChecker,
    private val accessChecker: AccessChecker,
    private val magicClient: MagicClient,
    private val ruleBookClient: RuleBookClient,
    private val characterEventPublisher: CharacterEventPublisher,
) {

    private fun ensureAccessToRoom(roomId: UUID) {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
    }

    private fun ensureAccessToSpellBook(spellBookId: UUID): SpellBookDto {
        val book = magicClient.getSpellBookById(spellBookId)
            ?: throw NotFoundException("Spell book not found by id: $spellBookId")
        val roomId = book.roomId ?: throw NotFoundException("Spell book has no roomId")
        ensureAccessToRoom(roomId)
        return book
    }

    private fun publishForBook(book: SpellBookDto) {
        val roomId = book.roomId ?: return
        val characterId = book.characterId ?: return
        characterEventPublisher.publishCharacterUpdated(roomId, characterId, CharacterEventType.SPELLBOOK_UPDATED)
    }

    private fun publishForBookId(spellBookId: UUID) {
        publishForBook(magicClient.getSpellBookById(spellBookId) ?: return)
    }

    private fun publishForCell(cell: SpellCellDto) {
        cell.spellBookId?.let { publishForBookId(it) }
    }

    private fun publishForBookItem(item: SpellBookItemDto) {
        item.spellBookId?.let { publishForBookId(it) }
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

    fun listSpellsDnd2024(spellClass: String?): List<SpellDto> {
        return magicClient.listSpellsDnd2024(spellClass) ?: emptyList()
    }

    fun importSpells2024(): ImportResult {
        return magicClient.importSpells2024() ?: throw NotFoundException("Failed to import spells 2024")
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
        val result = magicClient.createSpellBook(spellBookDto) ?: throw NotFoundException("Failed to create spell book")
        publishForBook(result)
        return result
    }

    fun getSpellBookByRoomAndCharacter(roomId: UUID, characterId: UUID): SpellBookDto {
        ensureAccessToRoom(roomId)
        return magicClient.getSpellBookByRoomAndCharacter(roomId, characterId)
            ?: throw NotFoundException("Spell book not found for room: $roomId, character: $characterId")
    }

    fun getSpellBookById(spellBookId: UUID): SpellBookDto {
        return ensureAccessToSpellBook(spellBookId)
    }

    fun updateSpellBook(spellBookId: UUID, spellBookDto: SpellBookDto): SpellBookDto {
        ensureAccessToSpellBook(spellBookId)
        val result = magicClient.updateSpellBook(spellBookId, spellBookDto)
            ?: throw NotFoundException("Spell book not found by id: $spellBookId")
        publishForBook(result)
        return result
    }

    fun deleteSpellBook(spellBookId: UUID) {
        val book = ensureAccessToSpellBook(spellBookId)
        magicClient.deleteSpellBook(spellBookId)
        publishForBook(book)
    }

    fun addSpellToBook(spellBookId: UUID, spellId: UUID): SpellBookDto {
        ensureAccessToSpellBook(spellBookId)
        val result = magicClient.addSpellToBook(spellBookId, spellId)
            ?: throw NotFoundException("Failed to add spell to spell book")
        publishForBook(result)
        return result
    }

    fun removeSpellFromBook(spellBookId: UUID, spellId: UUID): SpellBookDto {
        ensureAccessToSpellBook(spellBookId)
        val result = magicClient.removeSpellFromBook(spellBookId, spellId)
            ?: throw NotFoundException("Failed to remove spell from spell book")
        publishForBook(result)
        return result
    }

    fun setSpellInUse(spellBookId: UUID, spellId: UUID, inUse: Boolean): SpellBookItemDto {
        val book = ensureAccessToSpellBook(spellBookId)
        val result = magicClient.setSpellInUse(spellBookId, spellId, inUse)
            ?: throw NotFoundException("Failed to set spell in-use")
        publishForBook(book)
        return result
    }

    fun createSpellCellForBook(spellBookId: UUID, spellCellDto: SpellCellDto): SpellCellDto {
        val book = ensureAccessToSpellBook(spellBookId)
        val result = magicClient.createSpellCellForBook(spellBookId, spellCellDto)
            ?: throw NotFoundException("Failed to create spell cell for book")
        publishForBook(book)
        return result
    }

    fun refillRest(spellBookId: UUID, request: RefillRestRequest): SpellBookDto {
        val book = ensureAccessToSpellBook(spellBookId)
        val result = magicClient.refillRest(spellBookId, request)
            ?: throw NotFoundException("Failed to refill rest")
        publishForBook(book)
        return result
    }

    fun refillRestByCharacter(roomId: UUID, characterId: UUID, restType: String): SpellBookDto {
        ensureAccessToRoom(roomId)
        val result = magicClient.refillRestByCharacter(roomId, characterId, restType)
            ?: throw NotFoundException("Failed to refill rest by character")
        characterEventPublisher.publishCharacterUpdated(roomId, characterId, CharacterEventType.SPELLBOOK_UPDATED)
        return result
    }

    // Spell Book Items
    fun listSpellBookItems(): List<SpellBookItemDto> {
        return magicClient.listSpellBookItems() ?: emptyList()
    }

    fun createSpellBookItem(spellBookItemDto: SpellBookItemDto): SpellBookItemDto {
        spellBookItemDto.spellBookId?.let { ensureAccessToSpellBook(it) }
        val result = magicClient.createSpellBookItem(spellBookItemDto)
            ?: throw NotFoundException("Failed to create spell book item")
        publishForBookItem(result)
        return result
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
        val result = magicClient.updateSpellBookItem(id, spellBookItemDto)
            ?: throw NotFoundException("Spell book item not found by id: $id")
        publishForBookItem(result)
        return result
    }

    fun deleteSpellBookItem(id: UUID) {
        val item = magicClient.getSpellBookItemById(id)
            ?: throw NotFoundException("Spell book item not found by id: $id")
        item.spellBookId?.let { ensureAccessToSpellBook(it) }
        magicClient.deleteSpellBookItem(id)
        publishForBookItem(item)
    }

    fun setSpellBookItemInUse(id: UUID, inUse: Boolean): SpellBookItemDto {
        val item = magicClient.getSpellBookItemById(id)
            ?: throw NotFoundException("Spell book item not found by id: $id")
        item.spellBookId?.let { ensureAccessToSpellBook(it) }
        val result = magicClient.setSpellBookItemInUse(id, inUse)
            ?: throw NotFoundException("Spell book item not found by id: $id")
        publishForBookItem(result)
        return result
    }

    // Spell Cells
    fun listSpellCells(): List<SpellCellDto> {
        return magicClient.listSpellCells() ?: emptyList()
    }

    fun createSpellCell(spellCellDto: SpellCellDto): SpellCellDto {
        spellCellDto.spellBookId?.let { ensureAccessToSpellBook(it) }
        val result = magicClient.createSpellCell(spellCellDto)
            ?: throw NotFoundException("Failed to create spell cell")
        publishForCell(result)
        return result
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
        val result = magicClient.updateSpellCell(id, spellCellDto)
            ?: throw NotFoundException("Spell cell not found by id: $id")
        publishForCell(result)
        return result
    }

    fun deleteSpellCell(id: UUID) {
        val cell = magicClient.getSpellCellById(id)
            ?: throw NotFoundException("Spell cell not found by id: $id")
        cell.spellBookId?.let { ensureAccessToSpellBook(it) }
        magicClient.deleteSpellCell(id)
        publishForCell(cell)
    }

    fun useSpellCell(id: UUID): SpellCellDto {
        val cell = magicClient.getSpellCellById(id)
            ?: throw NotFoundException("Spell cell not found by id: $id")
        cell.spellBookId?.let { ensureAccessToSpellBook(it) }
        val result = magicClient.useSpellCell(id)
            ?: throw NotFoundException("Failed to use spell cell")
        publishForCell(result)
        return result
    }
}

package com.jiubredeemer.app.integration.magic

import com.jiubredeemer.app.integration.configuration.MagicProperty
import com.jiubredeemer.app.integration.magic.dto.ImportResult
import com.jiubredeemer.app.integration.magic.dto.RefillRestRequest
import com.jiubredeemer.app.integration.magic.dto.SpellBookDto
import com.jiubredeemer.app.integration.magic.dto.SpellBookItemDto
import com.jiubredeemer.app.integration.magic.dto.SpellCellDto
import com.jiubredeemer.app.integration.magic.dto.SpellDto
import com.jiubredeemer.common.exception.IntegrationAccessException
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

@Service
class MagicClient(
    private val restClient: RestClient,
    private val magicProperty: MagicProperty,
) {
    private val headers = HttpHeaders().apply { set("Content-Type", "application/json") }

    // Spells
    fun listSpells(spellClass: String? = null): List<SpellDto>? {
        return try {
            val builder = UriComponentsBuilder
                .fromHttpUrl(magicProperty.baseUrl)
                .pathSegment(magicProperty.apiUrl)
                .pathSegment(magicProperty.spellsUrl)
            spellClass?.let { builder.queryParam("spellClass", it) }
            val uri = builder.toUriString()
            restClient.get()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
                .toEntity(object : ParameterizedTypeReference<List<SpellDto>>() {})
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("Magic don't response on listSpells, cause: ${e.message}")
        }
    }

    fun createSpell(spellDto: SpellDto): SpellDto? {
        return try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(magicProperty.baseUrl)
                .pathSegment(magicProperty.apiUrl)
                .pathSegment(magicProperty.spellsUrl)
                .toUriString()
            restClient.post()
                .uri(uri)
                .headers { it.addAll(headers) }
                .body(spellDto)
                .retrieve()
                .toEntity(SpellDto::class.java)
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("Magic don't response on createSpell, cause: ${e.message}")
        }
    }

    fun importSpells(): ImportResult? {
        return try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(magicProperty.baseUrl)
                .pathSegment(magicProperty.apiUrl)
                .pathSegment(magicProperty.spellsUrl)
                .pathSegment(magicProperty.importUrl)
                .toUriString()
            restClient.post()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
                .toEntity(ImportResult::class.java)
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("Magic don't response on importSpells, cause: ${e.message}")
        }
    }

    fun getSpellById(id: UUID): SpellDto? {
        return try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(magicProperty.baseUrl)
                .pathSegment(magicProperty.apiUrl)
                .pathSegment(magicProperty.spellsUrl)
                .pathSegment(id.toString())
                .toUriString()
            restClient.get()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
                .toEntity(SpellDto::class.java)
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("Magic don't response on getSpellById, cause: ${e.message}")
        }
    }

    fun updateSpell(id: UUID, spellDto: SpellDto): SpellDto? {
        return try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(magicProperty.baseUrl)
                .pathSegment(magicProperty.apiUrl)
                .pathSegment(magicProperty.spellsUrl)
                .pathSegment(id.toString())
                .toUriString()
            restClient.put()
                .uri(uri)
                .headers { it.addAll(headers) }
                .body(spellDto)
                .retrieve()
                .toEntity(SpellDto::class.java)
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("Magic don't response on updateSpell, cause: ${e.message}")
        }
    }

    fun deleteSpell(id: UUID) {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(magicProperty.baseUrl)
                .pathSegment(magicProperty.apiUrl)
                .pathSegment(magicProperty.spellsUrl)
                .pathSegment(id.toString())
                .toUriString()
            restClient.delete()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
        } catch (e: Exception) {
            throw IntegrationAccessException("Magic don't response on deleteSpell, cause: ${e.message}")
        }
    }

    // Spell Books
    fun listSpellBooks(): List<SpellBookDto>? {
        return try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(magicProperty.baseUrl)
                .pathSegment(magicProperty.apiUrl)
                .pathSegment(magicProperty.spellBooksUrl)
                .toUriString()
            restClient.get()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
                .toEntity(object : ParameterizedTypeReference<List<SpellBookDto>>() {})
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("Magic don't response on listSpellBooks, cause: ${e.message}")
        }
    }

    fun createSpellBook(spellBookDto: SpellBookDto): SpellBookDto? {
        return try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(magicProperty.baseUrl)
                .pathSegment(magicProperty.apiUrl)
                .pathSegment(magicProperty.spellBooksUrl)
                .toUriString()
            restClient.post()
                .uri(uri)
                .headers { it.addAll(headers) }
                .body(spellBookDto)
                .retrieve()
                .toEntity(SpellBookDto::class.java)
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("Magic don't response on createSpellBook, cause: ${e.message}")
        }
    }

    fun getSpellBookByRoomAndCharacter(roomId: UUID, characterId: UUID): SpellBookDto? {
        return try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(magicProperty.baseUrl)
                .pathSegment(magicProperty.apiUrl)
                .pathSegment(magicProperty.spellBooksUrl)
                .pathSegment(magicProperty.byRoomCharacterUrl)
                .queryParam("roomId", roomId)
                .queryParam("characterId", characterId)
                .toUriString()
            restClient.get()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
                .toEntity(SpellBookDto::class.java)
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("Magic don't response on getSpellBookByRoomAndCharacter, cause: ${e.message}")
        }
    }

    fun getSpellBookById(spellBookId: UUID): SpellBookDto? {
        return try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(magicProperty.baseUrl)
                .pathSegment(magicProperty.apiUrl)
                .pathSegment(magicProperty.spellBooksUrl)
                .pathSegment(spellBookId.toString())
                .toUriString()
            restClient.get()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
                .toEntity(SpellBookDto::class.java)
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("Magic don't response on getSpellBookById, cause: ${e.message}")
        }
    }

    fun updateSpellBook(spellBookId: UUID, spellBookDto: SpellBookDto): SpellBookDto? {
        return try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(magicProperty.baseUrl)
                .pathSegment(magicProperty.apiUrl)
                .pathSegment(magicProperty.spellBooksUrl)
                .pathSegment(spellBookId.toString())
                .toUriString()
            restClient.put()
                .uri(uri)
                .headers { it.addAll(headers) }
                .body(spellBookDto)
                .retrieve()
                .toEntity(SpellBookDto::class.java)
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("Magic don't response on updateSpellBook, cause: ${e.message}")
        }
    }

    fun deleteSpellBook(spellBookId: UUID) {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(magicProperty.baseUrl)
                .pathSegment(magicProperty.apiUrl)
                .pathSegment(magicProperty.spellBooksUrl)
                .pathSegment(spellBookId.toString())
                .toUriString()
            restClient.delete()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
        } catch (e: Exception) {
            throw IntegrationAccessException("Magic don't response on deleteSpellBook, cause: ${e.message}")
        }
    }

    fun addSpellToBook(spellBookId: UUID, spellId: UUID): SpellBookDto? {
        return try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(magicProperty.baseUrl)
                .pathSegment(magicProperty.apiUrl)
                .pathSegment(magicProperty.spellBooksUrl)
                .pathSegment(spellBookId.toString())
                .pathSegment(magicProperty.spellsUrl)
                .pathSegment(spellId.toString())
                .toUriString()
            restClient.post()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
                .toEntity(SpellBookDto::class.java)
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("Magic don't response on addSpellToBook, cause: ${e.message}")
        }
    }

    fun removeSpellFromBook(spellBookId: UUID, spellId: UUID): SpellBookDto? {
        return try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(magicProperty.baseUrl)
                .pathSegment(magicProperty.apiUrl)
                .pathSegment(magicProperty.spellBooksUrl)
                .pathSegment(spellBookId.toString())
                .pathSegment(magicProperty.spellsUrl)
                .pathSegment(spellId.toString())
                .toUriString()
            restClient.delete()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
                .toEntity(SpellBookDto::class.java)
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("Magic don't response on removeSpellFromBook, cause: ${e.message}")
        }
    }

    fun setSpellInUse(spellBookId: UUID, spellId: UUID, inUse: Boolean): SpellBookItemDto? {
        return try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(magicProperty.baseUrl)
                .pathSegment(magicProperty.apiUrl)
                .pathSegment(magicProperty.spellBooksUrl)
                .pathSegment(spellBookId.toString())
                .pathSegment(magicProperty.spellsUrl)
                .pathSegment(spellId.toString())
                .pathSegment(magicProperty.inUseUrl)
                .queryParam("inUse", inUse)
                .toUriString()
            restClient.patch()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
                .toEntity(SpellBookItemDto::class.java)
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("Magic don't response on setSpellInUse, cause: ${e.message}")
        }
    }

    fun createSpellCellForBook(spellBookId: UUID, spellCellDto: SpellCellDto): SpellCellDto? {
        return try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(magicProperty.baseUrl)
                .pathSegment(magicProperty.apiUrl)
                .pathSegment(magicProperty.spellBooksUrl)
                .pathSegment(spellBookId.toString())
                .pathSegment(magicProperty.spellCellsUrl)
                .toUriString()
            restClient.post()
                .uri(uri)
                .headers { it.addAll(headers) }
                .body(spellCellDto)
                .retrieve()
                .toEntity(SpellCellDto::class.java)
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("Magic don't response on createSpellCellForBook, cause: ${e.message}")
        }
    }

    fun refillRest(spellBookId: UUID, request: RefillRestRequest): SpellBookDto? {
        return try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(magicProperty.baseUrl)
                .pathSegment(magicProperty.apiUrl)
                .pathSegment(magicProperty.spellBooksUrl)
                .pathSegment(spellBookId.toString())
                .pathSegment("rest")
                .toUriString()
            restClient.post()
                .uri(uri)
                .headers { it.addAll(headers) }
                .body(request)
                .retrieve()
                .toEntity(SpellBookDto::class.java)
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("Magic don't response on refillRest, cause: ${e.message}")
        }
    }

    fun refillRestByCharacter(roomId: UUID, characterId: UUID, restType: String): SpellBookDto? {
        return try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(magicProperty.baseUrl)
                .pathSegment(magicProperty.apiUrl)
                .pathSegment(magicProperty.spellBooksUrl)
                .pathSegment(magicProperty.byRoomCharacterUrl)
                .pathSegment("rest")
                .queryParam("roomId", roomId)
                .queryParam("characterId", characterId)
                .queryParam("restType", restType)
                .toUriString()
            restClient.post()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
                .toEntity(SpellBookDto::class.java)
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("Magic don't response on refillRestByCharacter, cause: ${e.message}")
        }
    }

    // Spell Book Items
    fun listSpellBookItems(): List<SpellBookItemDto>? {
        return try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(magicProperty.baseUrl)
                .pathSegment(magicProperty.apiUrl)
                .pathSegment(magicProperty.spellBookItemsUrl)
                .toUriString()
            restClient.get()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
                .toEntity(object : ParameterizedTypeReference<List<SpellBookItemDto>>() {})
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("Magic don't response on listSpellBookItems, cause: ${e.message}")
        }
    }

    fun createSpellBookItem(spellBookItemDto: SpellBookItemDto): SpellBookItemDto? {
        return try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(magicProperty.baseUrl)
                .pathSegment(magicProperty.apiUrl)
                .pathSegment(magicProperty.spellBookItemsUrl)
                .toUriString()
            restClient.post()
                .uri(uri)
                .headers { it.addAll(headers) }
                .body(spellBookItemDto)
                .retrieve()
                .toEntity(SpellBookItemDto::class.java)
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("Magic don't response on createSpellBookItem, cause: ${e.message}")
        }
    }

    fun getSpellBookItemById(id: UUID): SpellBookItemDto? {
        return try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(magicProperty.baseUrl)
                .pathSegment(magicProperty.apiUrl)
                .pathSegment(magicProperty.spellBookItemsUrl)
                .pathSegment(id.toString())
                .toUriString()
            restClient.get()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
                .toEntity(SpellBookItemDto::class.java)
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("Magic don't response on getSpellBookItemById, cause: ${e.message}")
        }
    }

    fun updateSpellBookItem(id: UUID, spellBookItemDto: SpellBookItemDto): SpellBookItemDto? {
        return try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(magicProperty.baseUrl)
                .pathSegment(magicProperty.apiUrl)
                .pathSegment(magicProperty.spellBookItemsUrl)
                .pathSegment(id.toString())
                .toUriString()
            restClient.put()
                .uri(uri)
                .headers { it.addAll(headers) }
                .body(spellBookItemDto)
                .retrieve()
                .toEntity(SpellBookItemDto::class.java)
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("Magic don't response on updateSpellBookItem, cause: ${e.message}")
        }
    }

    fun deleteSpellBookItem(id: UUID) {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(magicProperty.baseUrl)
                .pathSegment(magicProperty.apiUrl)
                .pathSegment(magicProperty.spellBookItemsUrl)
                .pathSegment(id.toString())
                .toUriString()
            restClient.delete()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
        } catch (e: Exception) {
            throw IntegrationAccessException("Magic don't response on deleteSpellBookItem, cause: ${e.message}")
        }
    }

    fun setSpellBookItemInUse(id: UUID, inUse: Boolean): SpellBookItemDto? {
        return try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(magicProperty.baseUrl)
                .pathSegment(magicProperty.apiUrl)
                .pathSegment(magicProperty.spellBookItemsUrl)
                .pathSegment(id.toString())
                .pathSegment(magicProperty.inUseUrl)
                .queryParam("inUse", inUse)
                .toUriString()
            restClient.patch()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
                .toEntity(SpellBookItemDto::class.java)
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("Magic don't response on setSpellBookItemInUse, cause: ${e.message}")
        }
    }

    // Spell Cells
    fun listSpellCells(): List<SpellCellDto>? {
        return try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(magicProperty.baseUrl)
                .pathSegment(magicProperty.apiUrl)
                .pathSegment(magicProperty.spellCellsUrl)
                .toUriString()
            restClient.get()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
                .toEntity(object : ParameterizedTypeReference<List<SpellCellDto>>() {})
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("Magic don't response on listSpellCells, cause: ${e.message}")
        }
    }

    fun createSpellCell(spellCellDto: SpellCellDto): SpellCellDto? {
        return try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(magicProperty.baseUrl)
                .pathSegment(magicProperty.apiUrl)
                .pathSegment(magicProperty.spellCellsUrl)
                .toUriString()
            restClient.post()
                .uri(uri)
                .headers { it.addAll(headers) }
                .body(spellCellDto)
                .retrieve()
                .toEntity(SpellCellDto::class.java)
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("Magic don't response on createSpellCell, cause: ${e.message}")
        }
    }

    fun getSpellCellById(id: UUID): SpellCellDto? {
        return try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(magicProperty.baseUrl)
                .pathSegment(magicProperty.apiUrl)
                .pathSegment(magicProperty.spellCellsUrl)
                .pathSegment(id.toString())
                .toUriString()
            restClient.get()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
                .toEntity(SpellCellDto::class.java)
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("Magic don't response on getSpellCellById, cause: ${e.message}")
        }
    }

    fun updateSpellCell(id: UUID, spellCellDto: SpellCellDto): SpellCellDto? {
        return try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(magicProperty.baseUrl)
                .pathSegment(magicProperty.apiUrl)
                .pathSegment(magicProperty.spellCellsUrl)
                .pathSegment(id.toString())
                .toUriString()
            restClient.put()
                .uri(uri)
                .headers { it.addAll(headers) }
                .body(spellCellDto)
                .retrieve()
                .toEntity(SpellCellDto::class.java)
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("Magic don't response on updateSpellCell, cause: ${e.message}")
        }
    }

    fun deleteSpellCell(id: UUID) {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(magicProperty.baseUrl)
                .pathSegment(magicProperty.apiUrl)
                .pathSegment(magicProperty.spellCellsUrl)
                .pathSegment(id.toString())
                .toUriString()
            restClient.delete()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
        } catch (e: Exception) {
            throw IntegrationAccessException("Magic don't response on deleteSpellCell, cause: ${e.message}")
        }
    }

    fun useSpellCell(id: UUID): SpellCellDto? {
        return try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(magicProperty.baseUrl)
                .pathSegment(magicProperty.apiUrl)
                .pathSegment(magicProperty.spellCellsUrl)
                .pathSegment(id.toString())
                .pathSegment("use")
                .toUriString()
            restClient.post()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
                .toEntity(SpellCellDto::class.java)
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("Magic don't response on useSpellCell, cause: ${e.message}")
        }
    }
}

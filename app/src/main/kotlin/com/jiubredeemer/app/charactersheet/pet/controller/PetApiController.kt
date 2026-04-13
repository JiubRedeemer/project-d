package com.jiubredeemer.app.charactersheet.pet.controller

import com.jiubredeemer.app.charactersheet.pet.dto.PetDto
import com.jiubredeemer.app.charactersheet.pet.dto.PetSkillDto
import com.jiubredeemer.app.charactersheet.pet.service.PetApiService
import com.jiubredeemer.app.integration.charactersheet.dto.character.BonusValueUpdateRequest
import com.jiubredeemer.app.integration.charactersheet.dto.pet.CreatePetRequest
import com.jiubredeemer.app.integration.charactersheet.dto.pet.PetHealthCurrentUpdateRequest
import com.jiubredeemer.app.integration.charactersheet.dto.pet.PetProfileUpdateRequest
import com.jiubredeemer.app.integration.charactersheet.dto.pet.PetSkillRequest
import com.jiubredeemer.auth.annotation.HasRoleOrThrow
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/api/rooms/{roomId}/characters/{characterId}/pets")
@Tag(name = "Pets", description = "API for character pets (room scoped)")
class PetApiController(
    private val petApiService: PetApiService,
) {
    @PostMapping("")
    @HasRoleOrThrow("ADMIN", "USER")
    fun createPetForRoom(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @RequestBody request: CreatePetRequest,
    ): PetDto? {
        return petApiService.createPetForRoom(roomId, characterId, request)
    }

    @GetMapping("")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getPetsByCharacterIdForRoom(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
    ): List<PetDto>? {
        return petApiService.getPetsByCharacterIdForRoom(roomId, characterId)
    }

    @GetMapping("/{petId}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getPetByIdForRoom(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @PathVariable petId: UUID,
    ): PetDto {
        return petApiService.getPetByIdForRoom(roomId, petId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found")
    }

    @DeleteMapping("/{petId}/logical")
    @HasRoleOrThrow("ADMIN", "USER")
    fun deletePetLogicalByIdForRoom(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @PathVariable petId: UUID,
    ) {
        petApiService.deletePetLogicalByIdForRoom(roomId, petId)
    }

    @PatchMapping("/{petId}/profile")
    @HasRoleOrThrow("ADMIN", "USER")
    fun updatePetProfileForRoom(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @PathVariable petId: UUID,
        @RequestBody request: PetProfileUpdateRequest,
    ) {
        petApiService.updatePetProfileForRoom(roomId, petId, request)
    }

    @PatchMapping("/{petId}/abilities/{abilityCode}/bonus")
    @HasRoleOrThrow("ADMIN", "USER")
    fun updatePetAbilityBonusForRoom(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @PathVariable petId: UUID,
        @PathVariable abilityCode: String,
        @RequestBody request: BonusValueUpdateRequest,
    ) {
        petApiService.updatePetAbilityBonusForRoom(roomId, petId, abilityCode, request)
    }

    @PatchMapping("/{petId}/health/current")
    @HasRoleOrThrow("ADMIN", "USER")
    fun updatePetCurrentHpForRoom(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @PathVariable petId: UUID,
        @RequestBody request: PetHealthCurrentUpdateRequest,
    ) {
        petApiService.updatePetCurrentHpForRoom(roomId, petId, request)
    }

    @PatchMapping("/{petId}/health/max")
    @HasRoleOrThrow("ADMIN", "USER")
    fun updatePetMaxHpForRoom(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @PathVariable petId: UUID,
        @RequestBody request: BonusValueUpdateRequest,
    ) {
        petApiService.updatePetMaxHpForRoom(roomId, petId, request)
    }

    @PostMapping("/{petId}/skills")
    @HasRoleOrThrow("ADMIN", "USER")
    fun createPetSkillForRoom(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @PathVariable petId: UUID,
        @RequestBody request: PetSkillRequest,
    ): PetSkillDto? {
        return petApiService.createPetSkillForRoom(roomId, petId, request)
    }

    @PatchMapping("/{petId}/skills/{skillId}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun updatePetSkillForRoom(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @PathVariable petId: UUID,
        @PathVariable skillId: UUID,
        @RequestBody request: PetSkillRequest,
    ): PetSkillDto? {
        return petApiService.updatePetSkillForRoom(roomId, petId, skillId, request)
    }

    @DeleteMapping("/{petId}/skills/{skillId}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun deletePetSkillForRoom(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @PathVariable petId: UUID,
        @PathVariable skillId: UUID,
    ) {
        petApiService.deletePetSkillForRoom(roomId, petId, skillId)
    }
}

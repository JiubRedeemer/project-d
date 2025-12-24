package com.jiubredeemer.app.charactersheet.character.controller

import com.jiubredeemer.app.charactersheet.character.dto.CharacterBioUpdateRequest
import com.jiubredeemer.app.charactersheet.character.dto.CharacterDto
import com.jiubredeemer.app.charactersheet.character.dto.CharacterSkillsDto
import com.jiubredeemer.app.charactersheet.character.dto.UpdateCurrentHealthRequest
import com.jiubredeemer.app.charactersheet.character.service.CharacterApiService
import com.jiubredeemer.app.integration.charactersheet.dto.character.BonusValueUpdateRequest
import com.jiubredeemer.app.integration.charactersheet.dto.character.CreateCharacterRequest
import com.jiubredeemer.app.integration.charactersheet.dto.character.UpdateMasteryRequest
import com.jiubredeemer.app.integration.dto.RestTypeEnum
import com.jiubredeemer.auth.annotation.HasRoleOrThrow
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/rooms/{roomId}/characters")
@Tag(name = "Персонажи", description = "API для управления персонажами")
class CharacterApiController(
    private val characterApiService: CharacterApiService
) {


    @PutMapping("")
    @HasRoleOrThrow("ADMIN", "USER")
    fun create(
        @PathVariable roomId: UUID,
        @RequestBody createCharacterRequest: CreateCharacterRequest
    ): CharacterDto? {
        return characterApiService.createCharacter(roomId, createCharacterRequest)
    }

    @GetMapping("")
    @HasRoleOrThrow("ADMIN", "USER")
    fun findAllByRoomIdAndUserId(
        @PathVariable roomId: UUID,
    ): List<CharacterDto>? {
        return characterApiService.findAllByRoomIdAndUserId(roomId)
    }

    @GetMapping("/{characterId}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun findByCharacterId(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
    ): CharacterDto? {
        return characterApiService.findByCharacterId(roomId, characterId)
    }

    @GetMapping("/{characterId}/header")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getHeaderInfoByCharacterId(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
    ): CharacterDto? {
        return characterApiService.getHeaderInfoByCharacterId(roomId, characterId)
    }

    @GetMapping("/{characterId}/subheader")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getSubheaderInfoByCharacterId(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
    ): CharacterDto? {
        return characterApiService.getSubheaderInfoByCharacterId(roomId, characterId)
    }

    @GetMapping("/{characterId}/abilities")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getAbilitiesAndSkillsInfoByCharacterId(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
    ): CharacterDto? {
        return characterApiService.getAbilitiesAndSkillsInfoByCharacterId(roomId, characterId)
    }

    @GetMapping("/{characterId}/bio")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getBioByCharacterId(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
    ): CharacterDto? {
        return characterApiService.getBioByCharacterId(roomId, characterId)
    }

    @PatchMapping("/{characterId}/bio/{section}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun updateBioByCharacterId(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @PathVariable section: String,
        @RequestBody characterBioUpdateRequest: CharacterBioUpdateRequest,
    ): CharacterDto? {
        return characterApiService.updateBioByCharacterId(roomId, characterId, section, characterBioUpdateRequest)
    }

    @PatchMapping("/{characterId}/abilities/{code}/bonus")
    @HasRoleOrThrow("ADMIN", "USER")
    fun updateAbilityBonusValue(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @PathVariable code: String,
        @RequestBody bonusValueUpdateRequest: BonusValueUpdateRequest
    ) {
        characterApiService.updateAbilityBonusValue(roomId, characterId, code, bonusValueUpdateRequest)
    }

    @PatchMapping("/{characterId}/health/bonus")
    @HasRoleOrThrow("ADMIN", "USER")
    fun updateHealthBonusValue(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @RequestBody bonusValueUpdateRequest: BonusValueUpdateRequest
    ) {
        characterApiService.updateHealthBonusValue(roomId, characterId, bonusValueUpdateRequest)
    }

    @PatchMapping("/{characterId}/health/updateCurrent")
    @HasRoleOrThrow("ADMIN", "USER")
    fun updateCurrentHealthById(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @RequestBody updateCurrentHealthRequest: UpdateCurrentHealthRequest
    ) {
        characterApiService.updateCurrentHealthById(roomId, characterId, updateCurrentHealthRequest)
    }

    @PatchMapping("/{characterId}/armoryClass/bonus")
    @HasRoleOrThrow("ADMIN", "USER")
    fun updateArmoryClassBonusValue(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @RequestBody bonusValueUpdateRequest: BonusValueUpdateRequest
    ) {
        characterApiService.updateArmoryClassBonusValue(roomId, characterId, bonusValueUpdateRequest)
    }

    @PatchMapping("/{characterId}/speed/bonus")
    @HasRoleOrThrow("ADMIN", "USER")
    fun updateSpeedBonusValue(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @RequestBody bonusValueUpdateRequest: BonusValueUpdateRequest
    ) {
        characterApiService.updateSpeedBonusValue(roomId, characterId, bonusValueUpdateRequest)
    }

    @PatchMapping("/{characterId}/initiative/bonus")
    @HasRoleOrThrow("ADMIN", "USER")
    fun updateInitiativeBonusValue(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @RequestBody bonusValueUpdateRequest: BonusValueUpdateRequest
    ) {
        characterApiService.updateInitiativeBonusValue(roomId, characterId, bonusValueUpdateRequest)
    }

    @PatchMapping("/{characterId}/skills/{code}/mastery")
    @HasRoleOrThrow("ADMIN", "USER")
    fun updateSkillMasteryByCode(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @PathVariable code: String,
        @RequestBody request: UpdateMasteryRequest
    ) {
        characterApiService.updateSkillMasteryByCode(roomId, characterId, code, request)
    }

    @GetMapping("/{characterId}/character-skills")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getCharacterSkills(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID
    ): List<CharacterSkillsDto>? {
        return characterApiService.getCharacterSkills(roomId, characterId)
    }

    @PutMapping("/{characterId}/character-skills")
    @HasRoleOrThrow("ADMIN", "USER")
    fun saveCharacterSkill(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @RequestBody characterSkillsDto: CharacterSkillsDto
    ): CharacterSkillsDto? {
        characterSkillsDto.characterId = characterId;
        return characterApiService.saveCharacterSkill(roomId, characterId, characterSkillsDto);
    }

    @DeleteMapping("/{characterId}/character-skills/{characterSkillId}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun deleteCharacterSkill(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @PathVariable characterSkillId: UUID
    ) {
        characterApiService.deleteCharacterSkill(roomId, characterId, characterSkillId);
    }

    @PatchMapping("/{characterId}/character-skills/{characterSkillId}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun updateCharacterSkill(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @PathVariable characterSkillId: UUID,
        @RequestBody characterSkillsDto: CharacterSkillsDto
    ): CharacterSkillsDto? {
        characterSkillsDto.id = characterSkillId;
        characterSkillsDto.characterId = characterId;
        return characterApiService.updateCharacterSkill(roomId, characterId, characterSkillId, characterSkillsDto);
    }

    @PatchMapping("/{characterId}/character-skills/{characterSkillId}/use")
    @HasRoleOrThrow("ADMIN", "USER")
    fun useCharacterSkill(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @PathVariable characterSkillId: UUID
    ): CharacterSkillsDto? {
        return characterApiService.useCharacterSkill(roomId, characterId, characterSkillId);
    }

    @PostMapping("/{characterId}/rest/{restType}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun characterRest(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @PathVariable restType: RestTypeEnum,
        @RequestBody hpDiceCount: Int
    ) {
      characterApiService.characterRest(roomId, characterId, restType, hpDiceCount)
    }


}

package com.jiubredeemer.app.room.service;

import com.jiubredeemer.app.integration.charactersheet.CharacterSheetClient;
import com.jiubredeemer.app.integration.dto.RuleTypeEnum;
import com.jiubredeemer.app.integration.dto.room.RoomCreateRequestDto;
import com.jiubredeemer.app.integration.itemstorage.ItemstorageClient;
import com.jiubredeemer.app.integration.magic.MagicClient;
import com.jiubredeemer.app.integration.notes.NotesClient;
import com.jiubredeemer.app.integration.rulebook.RuleBookClient;
import com.jiubredeemer.app.room.converter.RoomDtoConverter;
import com.jiubredeemer.app.room.model.request.CreateRoomRequest;
import com.jiubredeemer.app.room.model.response.CreateRoomResponse;
import com.jiubredeemer.app.room.model.response.RoomShortResponse;
import com.jiubredeemer.app.room.validator.RoomValidator;
import com.jiubredeemer.auth.service.AccessChecker;
import com.jiubredeemer.common.exception.IntegrationAccessException;
import com.jiubredeemer.dal.entity.Room;
import com.jiubredeemer.dal.model.UserDto;
import com.jiubredeemer.dal.service.RoomService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class RoomApiService {
    private final RoomDtoConverter roomDtoConverter;
    private final AccessChecker accessChecker;
    private final RoomService roomService;
    private final RoomValidator roomValidator;
    private final RuleBookClient ruleBookClient;
    private final CharacterSheetClient characterSheetClient;
    private final RoomAccessChecker roomAccessChecker;
    private final ItemstorageClient itemstorageClient;
    private final MagicClient magicClient;
    private final NotesClient notesClient;

    public RoomApiService(RoomDtoConverter roomDtoConverter,
                          AccessChecker accessChecker,
                          RoomService roomService,
                          RoomValidator roomValidator,
                          RuleBookClient ruleBookClient,
                          CharacterSheetClient characterSheetClient,
                          RoomAccessChecker roomAccessChecker, ItemstorageClient itemstorageClient, MagicClient magicClient, NotesClient notesClient) {
        this.roomDtoConverter = roomDtoConverter;
        this.accessChecker = accessChecker;
        this.roomService = roomService;
        this.roomValidator = roomValidator;
        this.ruleBookClient = ruleBookClient;
        this.characterSheetClient = characterSheetClient;
        this.roomAccessChecker = roomAccessChecker;
        this.itemstorageClient = itemstorageClient;
        this.magicClient = magicClient;
        this.notesClient = notesClient;
    }

    public CreateRoomResponse create(CreateRoomRequest request) {
        roomValidator.validateOnCreate(request);
        final UserDto currentUser = getCurrentUser();
        final Room createdRoom = roomService.create(roomDtoConverter.createRequestToRoomDto(request),
                Objects.requireNonNull(currentUser.getId()));
        persistRoomInModules(createdRoom, request.getRules(), request.getBaseRules());
        return new CreateRoomResponse(createdRoom.getId());
    }

    public List<RoomShortResponse> readAllForCurrentUser() {
        final UserDto currentUser = getCurrentUser();
        return roomService.readByUserId(Objects.requireNonNull(currentUser.getId()))
                .stream()
                .filter(roomDto -> roomDto.getDeleteDatetime() == null)
                .map(roomDtoConverter::roomDtoToShortRoom)
                .toList();
    }

    private void persistRoomInModules(Room createdRoom, RuleTypeEnum rules, RuleTypeEnum baseRule) {
        try {
            ruleBookClient.persistRoom(new RoomCreateRequestDto(
                    Objects.requireNonNull(createdRoom.getId()),
                    Objects.requireNonNull(Objects.requireNonNull(createdRoom.getOwner()).getId()),
                    rules,
                    baseRule));
            characterSheetClient.persistRoom(new RoomCreateRequestDto(createdRoom.getId(), createdRoom.getOwner().getId(), rules, baseRule));
        } catch (IntegrationAccessException integrationAccessException) {
            //Удаляем комнату везде, если какие то проблемы при создании
            System.err.println(integrationAccessException.getMessage());
            try {
                ruleBookClient.deleteRoom(Objects.requireNonNull(createdRoom.getId()));
            } catch (Exception ignored) {
            }
            try {
                characterSheetClient.deleteRoom(Objects.requireNonNull(createdRoom.getId()));
            } catch (Exception ignored) {
            }
            try {
                roomService.delete(Objects.requireNonNull(createdRoom.getId()));
            } catch (Exception ignored) {
            }
        }
    }


    private @NotNull UserDto getCurrentUser() {
        return accessChecker.getCurrentUser();
    }

    public void deleteRoom(@NotNull UUID roomId) {
        roomAccessChecker.hasAccessOrThrow(roomId, Objects.requireNonNull(accessChecker.getCurrentUser().getId()));

        try {
            ruleBookClient.logicDeleteById(Objects.requireNonNull(roomId));
            characterSheetClient.logicDeleteById(Objects.requireNonNull(roomId));
            itemstorageClient.logicDeleteById(Objects.requireNonNull(roomId));
            magicClient.logicDeleteById(Objects.requireNonNull(roomId));
            notesClient.logicDeleteById(Objects.requireNonNull(roomId));
            roomService.deleteLogical(Objects.requireNonNull(roomId));
        } catch (Exception exception) {
            throw new IntegrationAccessException(exception.getMessage());
        }
    }
}

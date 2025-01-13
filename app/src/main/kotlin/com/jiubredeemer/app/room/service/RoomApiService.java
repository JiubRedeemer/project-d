package com.jiubredeemer.app.room.service;

import com.jiubredeemer.app.integration.charactersheet.CharacterSheetClient;
import com.jiubredeemer.app.integration.rulebook.RuleBookClient;
import com.jiubredeemer.app.integration.dto.RuleTypeEnum;
import com.jiubredeemer.app.integration.dto.room.RoomCreateRequestDto;
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

@Service
public class RoomApiService {
    private final RoomDtoConverter roomDtoConverter;
    private final AccessChecker accessProcessor;
    private final RoomService roomService;
    private final RoomValidator roomValidator;
    private final RuleBookClient ruleBookClient;
    private final CharacterSheetClient characterSheetClient;

    public RoomApiService(RoomDtoConverter roomDtoConverter,
                          AccessChecker accessProcessor,
                          RoomService roomService, RoomValidator roomValidator, RuleBookClient ruleBookClient, CharacterSheetClient characterSheetClient) {
        this.roomDtoConverter = roomDtoConverter;
        this.accessProcessor = accessProcessor;
        this.roomService = roomService;
        this.roomValidator = roomValidator;
        this.ruleBookClient = ruleBookClient;
        this.characterSheetClient = characterSheetClient;
    }

    public CreateRoomResponse create(CreateRoomRequest request) {
        roomValidator.validateOnCreate(request);
        final UserDto currentUser = getCurrentUser();
        final Room createdRoom = roomService.create(roomDtoConverter.createRequestToRoomDto(request),
                Objects.requireNonNull(currentUser.getId()));
        persistRoomInModules(createdRoom);
        return new CreateRoomResponse(createdRoom.getId());
    }

    public List<RoomShortResponse> readAllForCurrentUser() {
        final UserDto currentUser = getCurrentUser();
        return roomService.readByUserId(Objects.requireNonNull(currentUser.getId()))
                .stream()
                .map(roomDtoConverter::roomDtoToShortRoom)
                .toList();
    }

    private void persistRoomInModules(Room createdRoom) {
        try {
            ruleBookClient.persistRoom(new RoomCreateRequestDto(
                    Objects.requireNonNull(createdRoom.getId()),
                    Objects.requireNonNull(Objects.requireNonNull(createdRoom.getOwner()).getId()),
                    RuleTypeEnum.DND5E));
            characterSheetClient.persistRoom(new RoomCreateRequestDto(createdRoom.getId(), createdRoom.getOwner().getId(), RuleTypeEnum.DND5E));
        } catch (IntegrationAccessException integrationAccessException) {
            //Удаляем комнату везде, если какие то проблемы при создании
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
        return accessProcessor.getCurrentUser();
    }
}

package com.jiubredeemer.app.rooms.service;

import com.jiubredeemer.app.integration.RuleBookClient;
import com.jiubredeemer.app.integration.dto.room.RoomCreateRequestDto;
import com.jiubredeemer.app.integration.dto.RuleTypeEnum;
import com.jiubredeemer.app.rooms.converter.RoomDtoConverter;
import com.jiubredeemer.app.rooms.model.request.CreateRoomRequest;
import com.jiubredeemer.app.rooms.model.response.CreateRoomResponse;
import com.jiubredeemer.app.rooms.model.response.RoomShortResponse;
import com.jiubredeemer.app.rooms.validator.RoomValidator;
import com.jiubredeemer.auth.service.AccessChecker;
import com.jiubredeemer.dal.entities.Room;
import com.jiubredeemer.dal.models.UserDto;
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

    public RoomApiService(RoomDtoConverter roomDtoConverter,
                          AccessChecker accessProcessor,
                          RoomService roomService, RoomValidator roomValidator, RuleBookClient ruleBookClient) {
        this.roomDtoConverter = roomDtoConverter;
        this.accessProcessor = accessProcessor;
        this.roomService = roomService;
        this.roomValidator = roomValidator;
        this.ruleBookClient = ruleBookClient;
    }

    public CreateRoomResponse create(CreateRoomRequest request) {
        roomValidator.validateOnCreate(request);
        final UserDto currentUser = getCurrentUser();
        final Room createdRoom = roomService.create(roomDtoConverter.createRequestToRoomDto(request),
                Objects.requireNonNull(currentUser.getId()));
        ruleBookClient.persistRoom(new RoomCreateRequestDto(createdRoom.getId(), createdRoom.getOwner().getId(), RuleTypeEnum.DND5E));
        return new CreateRoomResponse(createdRoom.getId());
    }

    public List<RoomShortResponse> readAllForCurrentUser() {
        final UserDto currentUser = getCurrentUser();
        return roomService.readByUserId(Objects.requireNonNull(currentUser.getId()))
                .stream()
                .map(roomDtoConverter::roomDtoToShortRoom)
                .toList();
    }

    private @NotNull UserDto getCurrentUser() {
        return accessProcessor.getCurrentUser();
    }
}

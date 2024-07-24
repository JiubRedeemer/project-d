package com.jiubredeemer.app.rooms.service;

import com.jiubredeemer.app.rooms.converter.RoomDtoConverter;
import com.jiubredeemer.app.rooms.model.request.CreateRoomRequest;
import com.jiubredeemer.app.rooms.model.response.CreateRoomResponse;
import com.jiubredeemer.auth.service.AccessChecker;
import com.jiubredeemer.dal.entities.Room;
import com.jiubredeemer.dal.entities.User;
import com.jiubredeemer.dal.service.RoomService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class RoomApiService {
    private final RoomDtoConverter roomDtoConverter;
    private final AccessChecker accessProcessor;
    private final RoomService roomService;

    public RoomApiService(RoomDtoConverter roomDtoConverter,
                          AccessChecker accessProcessor,
                          RoomService roomService) {
        this.roomDtoConverter = roomDtoConverter;
        this.accessProcessor = accessProcessor;
        this.roomService = roomService;
    }

    public CreateRoomResponse create(CreateRoomRequest request) {
        final User currentUser = getCurrentUser();
        final Room roomToCreate = roomDtoConverter.createRequestToRoom(request, currentUser);
        final Room createdRoom = roomService.create(roomToCreate, currentUser);
        return new CreateRoomResponse(createdRoom.getId());
    }

    private @NotNull User getCurrentUser() {
        return accessProcessor.getCurrentUser();
    }
}

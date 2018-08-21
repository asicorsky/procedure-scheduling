package com.procedure.scheduling.service.room;

import com.procedure.scheduling.dto.room.RoomDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface RoomService {

	RoomDto addRoom(RoomDto room);

	List<RoomDto> getRooms();
}

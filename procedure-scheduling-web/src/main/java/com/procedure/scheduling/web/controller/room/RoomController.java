package com.procedure.scheduling.web.controller.room;

import com.procedure.scheduling.service.room.RoomService;
import com.procedure.scheduling.web.Navigation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Navigation.ROOM)
public class RoomController {

	private final RoomService service;

	@Autowired
	public RoomController(RoomService service) {

		this.service = service;
	}

	@PostMapping(Navigation.LOAD_ALL)
	public ResponseEntity<?> getRooms() {

		var rooms = service.getRooms();
		return ResponseEntity.ok(rooms);
	}
}

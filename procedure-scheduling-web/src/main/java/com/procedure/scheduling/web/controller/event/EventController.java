package com.procedure.scheduling.web.controller.event;

import com.procedure.scheduling.dto.schedule.EventDto;
import com.procedure.scheduling.service.event.EventService;
import com.procedure.scheduling.web.Navigation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Navigation.EVENT)
public class EventController {

	private final EventService eventService;

	@Autowired
	public EventController(EventService eventService) {

		this.eventService = eventService;
	}

	@PostMapping(Navigation.LOAD_TODAY)
	public ResponseEntity<?> getEvents() {

		var events = eventService.getEventsForToday();
		return ResponseEntity.ok(events);
	}

	// no needed for current application because we send all the data in previous call (Navigation.LOAD_TODAY)
	// but it not related to real world (in real world we plan to use something like short dto objects)
	// So, do loading by id because of it.
	@PostMapping(Navigation.LOAD_BY_ID)
	public ResponseEntity<?> getEvent(@PathVariable(Navigation.ID_PATH_PARAM) long id) {

		EventDto event = eventService.getEvent(id);
		return ResponseEntity.ok(event);
	}

	@PostMapping(Navigation.NEW_EVENT_FOR_ROOM)
	public ResponseEntity<?> addEvent(@PathVariable(Navigation.ROOM_ID_PATH_PARAM) long roomId, @RequestBody EventDto event) {

		eventService.addEvent(event, roomId);
		return ResponseEntity.ok().build();
	}

	@PostMapping(Navigation.MODIFY_EVENT_FOR_ROOM)
	public ResponseEntity<?> modifyEvent(@PathVariable(Navigation.ROOM_ID_PATH_PARAM) long roomId, @RequestBody EventDto event) {

		eventService.changeEvent(event, roomId);
		return ResponseEntity.ok().build();
	}

}

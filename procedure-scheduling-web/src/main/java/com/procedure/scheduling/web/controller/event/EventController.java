package com.procedure.scheduling.web.controller.event;

import com.procedure.scheduling.service.event.EventService;
import com.procedure.scheduling.web.Navigation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}

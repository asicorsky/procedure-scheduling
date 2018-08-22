package com.procedure.scheduling.dto.schedule;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.procedure.scheduling.dto.room.RoomDto;

import java.util.List;

public class EventRowDto {

	private final RoomDto room;
	private final List<EventDto> events;

	@JsonCreator
	public EventRowDto(@JsonProperty("room") RoomDto room, @JsonProperty("events") List<EventDto> events) {

		this.room = room;
		this.events = events;
	}

	public RoomDto getRoom() {

		return room;
	}

	public List<EventDto> getEvents() {

		return events;
	}
}

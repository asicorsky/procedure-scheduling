package com.procedure.scheduling.dto.room;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RoomDto {

	private final Long id;
	private final String name;

	@JsonCreator
	public RoomDto(@JsonProperty("id") Long id, @JsonProperty("name") String name) {

		this.id = id;
		this.name = name;
	}

	public Long getId() {

		return id;
	}

	public String getName() {

		return name;
	}
}

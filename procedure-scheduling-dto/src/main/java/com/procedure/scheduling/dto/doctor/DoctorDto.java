package com.procedure.scheduling.dto.doctor;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DoctorDto {

	private final Long id;
	private final String name;

	@JsonCreator
	public DoctorDto(@JsonProperty("id") Long id, @JsonProperty("name") String name) {

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

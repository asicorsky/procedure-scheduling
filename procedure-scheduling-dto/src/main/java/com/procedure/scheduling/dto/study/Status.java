package com.procedure.scheduling.dto.study;

import com.fasterxml.jackson.annotation.JsonValue;
import com.procedure.scheduling.common.enumeration.EnumConvention;
import com.procedure.scheduling.common.exceptions.EnumerationException;

import java.util.Arrays;

public enum Status implements EnumConvention {

	Planned("Planned"),
	InProgress("In Progress"),
	Finished("Finished"),
	None("None");

	private final String identifier;

	Status(String identifier) {

		this.identifier = identifier;
	}

	public static Status byIdentifier(String identifier) {

		return Arrays.stream(values()).filter(value -> value.identifier.equals(identifier)).findAny().orElseThrow(() -> new EnumerationException(Status.class, identifier));
	}

	@Override
	@JsonValue
	public String identifier() {

		return identifier;
	}
}

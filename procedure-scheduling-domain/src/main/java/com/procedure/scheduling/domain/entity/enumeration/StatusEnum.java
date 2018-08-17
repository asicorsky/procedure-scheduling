package com.procedure.scheduling.domain.entity.enumeration;

import com.procedure.scheduling.common.enumeration.EnumConvention;
import com.procedure.scheduling.common.exceptions.EnumerationException;

import java.util.Arrays;

public enum StatusEnum implements EnumConvention {

	Planned("Planned"),
	InProgress("In Progress"),
	Finished("Finished");

	private final String identifier;

	StatusEnum(String identifier) {

		this.identifier = identifier;
	}

	public static StatusEnum byIdentifier(String identifier) {

		return Arrays.stream(values()).filter(value -> value.identifier.equals(identifier)).findAny().orElseThrow(() -> new EnumerationException(StatusEnum.class, identifier));
	}

	@Override
	public String identifier() {

		return identifier;
	}
}

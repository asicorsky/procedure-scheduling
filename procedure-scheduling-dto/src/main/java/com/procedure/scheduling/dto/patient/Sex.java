package com.procedure.scheduling.dto.patient;

import com.fasterxml.jackson.annotation.JsonValue;
import com.procedure.scheduling.common.enumeration.EnumConvention;
import com.procedure.scheduling.common.exceptions.EnumerationException;

import java.util.Arrays;

public enum Sex implements EnumConvention {

	Male("Male"),
	Female("Female");

	private final String identifier;

	Sex(String identifier) {

		this.identifier = identifier;
	}

	public static Sex byIdentifier(String identifier) {

		return Arrays.stream(values()).filter(value -> value.identifier.equals(identifier)).findAny().orElseThrow(() -> new EnumerationException(Sex.class, identifier));
	}

	@Override
	@JsonValue
	public String identifier() {

		return identifier;
	}
}

package com.procedure.scheduling.domain.entity.enumeration;

import com.procedure.scheduling.common.enumeration.EnumConvention;
import com.procedure.scheduling.common.exceptions.EnumerationException;

import java.util.Arrays;

public enum SexEnum implements EnumConvention {

	Male("Male"),
	Female("Female");

	private final String identifier;

	SexEnum(String identifier) {

		this.identifier = identifier;
	}

	public static SexEnum byIdentifier(String identifier) {

		return Arrays.stream(values()).filter(value -> value.identifier.equals(identifier)).findAny().orElseThrow(() -> new EnumerationException(SexEnum.class, identifier));
	}

	@Override
	public String identifier() {

		return identifier;
	}
}

package com.procedure.scheduling.common.exceptions;

public class EnumerationException extends ProcedureSchedulingException {

	public EnumerationException(Class<? extends Enum<?>> enumeration, String identifier) {

		super("Cannot create enum " + enumeration.getSimpleName() + " with " + identifier + " identifier");
	}
}

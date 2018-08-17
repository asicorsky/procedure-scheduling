package com.procedure.scheduling.common.exceptions;

public class NonInstanceException extends ProcedureSchedulingException {

	public NonInstanceException(Class<?> clazz) {

		super("There is no instances for " + clazz.getSimpleName() + " class");
	}
}

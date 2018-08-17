package com.procedure.scheduling.common.exceptions;

public class ProcedureSchedulingException extends RuntimeException {

	public ProcedureSchedulingException() {

		super();
	}

	public ProcedureSchedulingException(String message) {

		super(message);
	}

	public ProcedureSchedulingException(String message, Throwable cause) {

		super(message, cause);
	}

	public ProcedureSchedulingException(Throwable cause) {

		super(cause);
	}
}

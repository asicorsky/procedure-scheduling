package com.procedure.scheduling.service.exceptions;

import com.procedure.scheduling.common.exceptions.ProcedureSchedulingException;

public class EventException extends ProcedureSchedulingException {

	public EventException() {

		super();
	}

	public EventException(String message) {

		super(message);
	}
}

package com.procedure.scheduling.common.utils;

import com.procedure.scheduling.common.exceptions.NonInstanceException;

public final class StringUtils {

	public static final String RIGHT_SLASH = "/";

	private StringUtils() {

		throw new NonInstanceException(StringUtils.class);
	}
}

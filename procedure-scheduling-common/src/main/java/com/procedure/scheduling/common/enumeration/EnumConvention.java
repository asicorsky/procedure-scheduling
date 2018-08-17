package com.procedure.scheduling.common.enumeration;

/**
 * Common interface between domain and dto objects for enum conversation.<br/>
 * Using one objects in different places if these places should be logically separated is a bad practice:<br/>
 * For example, we can create new custom Jackson annotation for enums for dto project, but using these objects for JPA mapping is not looks perfect.
 */
public interface EnumConvention {

	/**
	 * Returns common identifier between jpa-enum and dto-enum
	 *
	 * @return the identifier
	 */
	String identifier();
}

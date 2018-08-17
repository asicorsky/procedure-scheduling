package com.procedure.scheduling.service.mapper;

import com.procedure.scheduling.common.exceptions.NonInstanceException;
import com.procedure.scheduling.domain.entity.PatientEntity;
import com.procedure.scheduling.domain.entity.enumeration.SexEnum;

import java.util.Date;

// Use only object fields for mapping because of potential problems
// with JPA on "Detached entity passed to persist", for example
public final class EntityMapper {

	private EntityMapper() {

		throw new NonInstanceException(EntityMapper.class);
	}

	public static PatientEntity toPatientEntity(String name, SexEnum sex, Date dayOfBirth) {

		return new PatientEntity(name, sex, dayOfBirth);
	}
}

package com.procedure.scheduling.service.mapper;

import com.procedure.scheduling.common.exceptions.NonInstanceException;
import com.procedure.scheduling.domain.entity.PatientEntity;
import com.procedure.scheduling.dto.patient.PatientDto;
import com.procedure.scheduling.dto.patient.Sex;

import java.util.Objects;

public final class DtoMapper {

	private DtoMapper() {

		throw new NonInstanceException(DtoMapper.class);
	}

	public static PatientDto toPatientDto(PatientEntity entity) {

		if (Objects.isNull(entity)) {
			return null;
		}

		return new PatientDto(entity.getId(), entity.getName(), Sex.byIdentifier(entity.getSex().identifier()), entity.getDayOfBirth());

	}
}

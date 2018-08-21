package com.procedure.scheduling.service.mapper;

import com.procedure.scheduling.common.exceptions.NonInstanceException;
import com.procedure.scheduling.domain.entity.DoctorEntity;
import com.procedure.scheduling.domain.entity.PatientEntity;
import com.procedure.scheduling.domain.entity.RoomEntity;
import com.procedure.scheduling.domain.entity.StudyEntity;
import com.procedure.scheduling.domain.entity.enumeration.SexEnum;
import com.procedure.scheduling.domain.entity.enumeration.StatusEnum;

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

	public static RoomEntity toRoomEntity(String name) {

		return new RoomEntity(name);
	}

	public static DoctorEntity toDoctorEntity(String name) {

		return new DoctorEntity(name);
	}

	public static StudyEntity toStudyEntity(String description, PatientEntity patient, RoomEntity room, DoctorEntity doctor, StatusEnum status, Date plannedStartTime,
			Date estimatedEndTime) {

		return new StudyEntity(description, patient, room, doctor, status, plannedStartTime, estimatedEndTime);
	}
}

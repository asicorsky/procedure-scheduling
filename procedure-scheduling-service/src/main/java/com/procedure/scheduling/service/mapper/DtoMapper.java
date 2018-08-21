package com.procedure.scheduling.service.mapper;

import com.procedure.scheduling.common.exceptions.NonInstanceException;
import com.procedure.scheduling.domain.entity.DoctorEntity;
import com.procedure.scheduling.domain.entity.PatientEntity;
import com.procedure.scheduling.domain.entity.RoomEntity;
import com.procedure.scheduling.domain.entity.StudyEntity;
import com.procedure.scheduling.dto.doctor.DoctorDto;
import com.procedure.scheduling.dto.patient.PatientDto;
import com.procedure.scheduling.dto.patient.Sex;
import com.procedure.scheduling.dto.room.RoomDto;
import com.procedure.scheduling.dto.study.Status;
import com.procedure.scheduling.dto.study.StudyDto;

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

	public static RoomDto toRoomDto(RoomEntity entity) {

		if (Objects.isNull(entity)) {
			return null;
		}

		return new RoomDto(entity.getId(), entity.getName());
	}

	public static DoctorDto toDoctorDto(DoctorEntity entity) {

		if (Objects.isNull(entity)) {
			return null;
		}

		return new DoctorDto(entity.getId(), entity.getName());
	}

	public static StudyDto toStudyDto(StudyEntity entity) {

		if (Objects.isNull(entity)) {
			return null;
		}

		PatientDto patient = toPatientDto(entity.getPatient());
		RoomDto room = toRoomDto(entity.getRoom());
		DoctorDto doctor = toDoctorDto(entity.getDoctor());

		return new StudyDto(entity.getId(), entity.getDescription(), patient, room, doctor, Status.byIdentifier(entity.getStatus().identifier()), entity.getPlannedStartTime(),
				entity.getEstimatedEndTime());
	}
}

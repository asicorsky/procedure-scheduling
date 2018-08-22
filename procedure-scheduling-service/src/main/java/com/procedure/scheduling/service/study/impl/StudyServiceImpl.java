package com.procedure.scheduling.service.study.impl;

import com.procedure.scheduling.domain.entity.DoctorEntity;
import com.procedure.scheduling.domain.entity.PatientEntity;
import com.procedure.scheduling.domain.entity.RoomEntity;
import com.procedure.scheduling.domain.entity.StudyEntity;
import com.procedure.scheduling.domain.entity.enumeration.StatusEnum;
import com.procedure.scheduling.domain.exceptions.EntityNotFoundException;
import com.procedure.scheduling.domain.repository.DoctorRepository;
import com.procedure.scheduling.domain.repository.PatientRepository;
import com.procedure.scheduling.domain.repository.RoomRepository;
import com.procedure.scheduling.domain.repository.StudyRepository;
import com.procedure.scheduling.dto.room.RoomDto;
import com.procedure.scheduling.dto.study.StudyDto;
import com.procedure.scheduling.service.mapper.DtoMapper;
import com.procedure.scheduling.service.mapper.EntityMapper;
import com.procedure.scheduling.service.study.StudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudyServiceImpl implements StudyService {

	private final StudyRepository studyRepository;
	private final PatientRepository patientRepository;
	private final DoctorRepository doctorRepository;
	private final RoomRepository roomRepository;

	@Autowired
	public StudyServiceImpl(StudyRepository studyRepository, PatientRepository patientRepository, DoctorRepository doctorRepository, RoomRepository roomRepository) {

		this.studyRepository = studyRepository;
		this.patientRepository = patientRepository;
		this.doctorRepository = doctorRepository;
		this.roomRepository = roomRepository;
	}

	@Override
	public StudyDto addStudy(StudyDto study) {

		PatientEntity patient = patientRepository.findByName(study.getPatient().getName()).orElseThrow(EntityNotFoundException::new);
		DoctorEntity doctor = doctorRepository.findByName(study.getDoctor().getName()).orElseThrow(EntityNotFoundException::new);
		RoomEntity room = roomRepository.findByName(study.getRoom().getName()).orElseThrow(EntityNotFoundException::new);

		StudyEntity entity = EntityMapper
				.toStudyEntity(study.getDescription(), patient, room, doctor, StatusEnum.byIdentifier(study.getStatus().identifier()), study.getPlannedStartTime(),
						study.getEstimatedEndTime());

		studyRepository.save(entity);
		return DtoMapper.toStudyDto(entity);
	}

	@Override
	public List<StudyDto> getStudies() {

		return studyRepository.findAll().stream().map(DtoMapper::toStudyDto).collect(Collectors.toList());
	}

	@Override
	public List<StudyDto> getStudiesForRoom(Date from, Date to, RoomDto room) {

		RoomEntity entity = roomRepository.findByName(room.getName()).orElseThrow(EntityNotFoundException::new);
		return studyRepository.findOverlappedStudies(from, to, entity).stream().map(DtoMapper::toStudyDto).collect(Collectors.toList());
	}

	@Override
	public boolean isOverlapped(Date startDate, Date endDate, RoomDto room) {

		RoomEntity entity = roomRepository.findByName(room.getName()).orElseThrow(EntityNotFoundException::new);
		return !studyRepository.findOverlappedStudies(startDate, endDate, entity).isEmpty();
	}
}

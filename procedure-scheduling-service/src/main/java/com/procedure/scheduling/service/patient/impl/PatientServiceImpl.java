package com.procedure.scheduling.service.patient.impl;

import com.procedure.scheduling.domain.entity.PatientEntity;
import com.procedure.scheduling.domain.entity.enumeration.SexEnum;
import com.procedure.scheduling.domain.repository.PatientRepository;
import com.procedure.scheduling.dto.patient.PatientDto;
import com.procedure.scheduling.service.mapper.DtoMapper;
import com.procedure.scheduling.service.mapper.EntityMapper;
import com.procedure.scheduling.service.patient.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {

	private final PatientRepository patientRepository;

	@Autowired
	public PatientServiceImpl(PatientRepository patientRepository) {

		this.patientRepository = patientRepository;
	}

	@Override
	public PatientDto addPatient(PatientDto patient) {

		PatientEntity entity = EntityMapper.toPatientEntity(patient.getName(), SexEnum.byIdentifier(patient.getSex().identifier()), patient.getDayOfBirth());
		patientRepository.save(entity);
		return DtoMapper.toPatientDto(entity);
	}

	@Override
	public List<PatientDto> getAvailablePatients() {

		var entities = patientRepository.findAvailablePatients();
		return entities.stream().map(DtoMapper::toPatientDto).collect(Collectors.toList());
	}

	@Override
	public List<PatientDto> getPatients() {

		var entities = patientRepository.findAll();
		return entities.stream().map(DtoMapper::toPatientDto).collect(Collectors.toList());
	}
}


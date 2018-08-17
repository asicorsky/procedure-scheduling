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
}

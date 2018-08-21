package com.procedure.scheduling.service.doctor.impl;

import com.procedure.scheduling.domain.entity.DoctorEntity;
import com.procedure.scheduling.domain.repository.DoctorRepository;
import com.procedure.scheduling.dto.doctor.DoctorDto;
import com.procedure.scheduling.service.doctor.DoctorService;
import com.procedure.scheduling.service.mapper.DtoMapper;
import com.procedure.scheduling.service.mapper.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorServiceImpl implements DoctorService {

	private final DoctorRepository doctorRepository;

	@Autowired
	public DoctorServiceImpl(DoctorRepository doctorRepository) {

		this.doctorRepository = doctorRepository;
	}

	@Override
	public DoctorDto addDoctor(DoctorDto doctor) {

		DoctorEntity entity = EntityMapper.toDoctorEntity(doctor.getName());
		doctorRepository.save(entity);
		return DtoMapper.toDoctorDto(entity);
	}
}

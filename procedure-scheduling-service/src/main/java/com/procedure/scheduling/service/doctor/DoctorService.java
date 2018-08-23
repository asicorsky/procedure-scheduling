package com.procedure.scheduling.service.doctor;

import com.procedure.scheduling.dto.doctor.DoctorDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface DoctorService {

	DoctorDto addDoctor(DoctorDto doctor);

	List<DoctorDto> getAvailableDoctors();
}

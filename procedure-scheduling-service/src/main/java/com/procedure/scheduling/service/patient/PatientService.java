package com.procedure.scheduling.service.patient;

import com.procedure.scheduling.dto.patient.PatientDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface PatientService {

	PatientDto addPatient(PatientDto patient);

	List<PatientDto> getAvailablePatients();
}

package com.procedure.scheduling.web.controller.patient;

import com.procedure.scheduling.service.patient.PatientService;
import com.procedure.scheduling.web.Navigation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Navigation.PATIENT)
public class PatientController {

	private final PatientService service;

	@Autowired
	public PatientController(PatientService service) {

		this.service = service;
	}

	@PostMapping(Navigation.LOAD_AVAILABLE)
	public ResponseEntity<?> getAvailablePatients() {

		var patients = service.getAvailablePatients();
		return ResponseEntity.ok(patients);
	}

	@PostMapping(Navigation.LOAD_ALL)
	public ResponseEntity<?> getPatients() {

		var patients = service.getPatients();
		return ResponseEntity.ok(patients);
	}
}

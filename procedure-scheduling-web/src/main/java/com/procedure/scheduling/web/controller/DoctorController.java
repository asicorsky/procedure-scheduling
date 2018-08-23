package com.procedure.scheduling.web.controller;

import com.procedure.scheduling.service.doctor.DoctorService;
import com.procedure.scheduling.web.Navigation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Navigation.DOCTOR)
public class DoctorController {

	private final DoctorService service;

	@Autowired
	public DoctorController(DoctorService service) {

		this.service = service;
	}

	@PostMapping(Navigation.LOAD_AVAILABLE)
	public ResponseEntity<?> getAvailableDoctors() {

		var doctors = service.getAvailableDoctors();
		return ResponseEntity.ok(doctors);
	}
}

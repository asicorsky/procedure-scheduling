package com.procedure.scheduling.web.controller.study;

import com.procedure.scheduling.service.study.StudyService;
import com.procedure.scheduling.web.Navigation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Navigation.STUDY)
public class StudyController {

	private final StudyService service;

	@Autowired
	public StudyController(StudyService service) {

		this.service = service;
	}

	@PostMapping(Navigation.LOAD_ALL)
	public ResponseEntity<?> getStudies() {

		var studies = service.getStudies();
		return ResponseEntity.ok(studies);
	}
}

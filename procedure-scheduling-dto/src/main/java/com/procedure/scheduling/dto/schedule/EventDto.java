package com.procedure.scheduling.dto.schedule;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.procedure.scheduling.dto.doctor.DoctorDto;
import com.procedure.scheduling.dto.patient.PatientDto;
import com.procedure.scheduling.dto.study.Status;

import java.util.Date;

public class EventDto {

	private final Long id;
	private final String description;
	private final Status status;
	private final Date plannedStartTime;
	private final Date estimatedEndTime;
	private final DoctorDto doctor;
	private final PatientDto patient;

	@JsonCreator
	public EventDto(@JsonProperty("id") Long id, @JsonProperty("description") String description, @JsonProperty("status") Status status,
			@JsonProperty("plannedStartTime") Date plannedStartTime, @JsonProperty("estimatedEndTime") Date estimatedEndTime, @JsonProperty("doctor") DoctorDto doctor,
			@JsonProperty("patient") PatientDto patient) {

		this.id = id;
		this.description = description;
		this.status = status;
		this.plannedStartTime = plannedStartTime;
		this.estimatedEndTime = estimatedEndTime;
		this.doctor = doctor;
		this.patient = patient;
	}

	public String getDescription() {

		return description;
	}

	public Status getStatus() {

		return status;
	}

	public Date getPlannedStartTime() {

		return plannedStartTime;
	}

	public Date getEstimatedEndTime() {

		return estimatedEndTime;
	}

	public DoctorDto getDoctor() {

		return doctor;
	}

	public PatientDto getPatient() {

		return patient;
	}

	public Long getId() {

		return id;
	}
}

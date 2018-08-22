package com.procedure.scheduling.dto.study;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.procedure.scheduling.dto.doctor.DoctorDto;
import com.procedure.scheduling.dto.patient.PatientDto;
import com.procedure.scheduling.dto.room.RoomDto;

import java.util.Date;

public class StudyDto {

	private final Long id;
	private final String description;
	private final PatientDto patient;
	private final RoomDto room;
	private final DoctorDto doctor;
	private final Status status;
	private final Date plannedStartTime;
	private final Date estimatedEndTime;

	public StudyDto(@JsonProperty("id") Long id, @JsonProperty("description") String description, @JsonProperty("patient") PatientDto patient, @JsonProperty("room") RoomDto room,
			@JsonProperty("doctor") DoctorDto doctor, @JsonProperty("status") Status status, @JsonProperty("plannedStartTime") Date plannedStartTime,
			@JsonProperty("estimatedEndTime") Date estimatedEndTime) {

		this.id = id;
		this.description = description;
		this.patient = patient;
		this.room = room;
		this.doctor = doctor;
		this.status = status;
		this.plannedStartTime = plannedStartTime;
		this.estimatedEndTime = estimatedEndTime;
	}

	public Long getId() {

		return id;
	}

	public String getDescription() {

		return description;
	}

	public PatientDto getPatient() {

		return patient;
	}

	public RoomDto getRoom() {

		return room;
	}

	public DoctorDto getDoctor() {

		return doctor;
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
}

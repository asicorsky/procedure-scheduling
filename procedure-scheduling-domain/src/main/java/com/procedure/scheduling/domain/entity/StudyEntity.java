package com.procedure.scheduling.domain.entity;

import com.procedure.scheduling.domain.entity.enumeration.StatusEnum;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "study")
public class StudyEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String description;

	@ManyToOne
	@JoinColumn(name = "patient_id")
	private PatientEntity patient;

	@ManyToOne
	@JoinColumn(name = "room_id")
	private RoomEntity room;

	@ManyToOne
	@JoinColumn(name = "doctor_id")
	private DoctorEntity doctor;

	@Enumerated(EnumType.STRING)
	private StatusEnum status;

	private Date plannedStartTime;

	private Date estimatedEndTime;

	private StudyEntity() {

	}

	public StudyEntity(String description, PatientEntity patient, RoomEntity room, DoctorEntity doctor, StatusEnum status, Date plannedStartTime, Date estimatedEndTime) {

		this();
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

	public void setId(Long id) {

		this.id = id;
	}

	public String getDescription() {

		return description;
	}

	public void setDescription(String description) {

		this.description = description;
	}

	public PatientEntity getPatient() {

		return patient;
	}

	public void setPatient(PatientEntity patient) {

		this.patient = patient;
	}

	public RoomEntity getRoom() {

		return room;
	}

	public void setRoom(RoomEntity room) {

		this.room = room;
	}

	public DoctorEntity getDoctor() {

		return doctor;
	}

	public void setDoctor(DoctorEntity doctor) {

		this.doctor = doctor;
	}

	public StatusEnum getStatus() {

		return status;
	}

	public void setStatus(StatusEnum status) {

		this.status = status;
	}

	public Date getPlannedStartTime() {

		return plannedStartTime;
	}

	public void setPlannedStartTime(Date plannedStartTime) {

		this.plannedStartTime = plannedStartTime;
	}

	public Date getEstimatedEndTime() {

		return estimatedEndTime;
	}

	public void setEstimatedEndTime(Date estimatedEndTime) {

		this.estimatedEndTime = estimatedEndTime;
	}
}

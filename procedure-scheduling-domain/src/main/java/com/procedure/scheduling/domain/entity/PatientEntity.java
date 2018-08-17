package com.procedure.scheduling.domain.entity;

import com.procedure.scheduling.domain.entity.enumeration.SexEnum;

import javax.persistence.*;
import java.util.Date;

@Table
@Entity(name = "patients")
public class PatientEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@Enumerated(EnumType.STRING)
	private SexEnum sex;

	private Date dayOfBirth;

	// for reflective calls from JPA on entity creation
	private PatientEntity() {

	}

	public PatientEntity(String name, SexEnum sex, Date dayOfBirth) {

		this();
		this.name = name;
		this.sex = sex;
		this.dayOfBirth = dayOfBirth;
	}

	public Long getId() {

		return id;
	}

	public void setId(Long id) {

		this.id = id;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public SexEnum getSex() {

		return sex;
	}

	public void setSex(SexEnum sex) {

		this.sex = sex;
	}

	public Date getDayOfBirth() {

		return dayOfBirth;
	}

	public void setDayOfBirth(Date dayOfBirth) {

		this.dayOfBirth = dayOfBirth;
	}
}

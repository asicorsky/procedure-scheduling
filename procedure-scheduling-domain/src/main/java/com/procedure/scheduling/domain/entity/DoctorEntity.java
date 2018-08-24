package com.procedure.scheduling.domain.entity;

import javax.persistence.*;

@Entity
@Table(name = "doctors")
public class DoctorEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private DoctorEntity() {

	}

	public DoctorEntity(String name) {

		this();
		this.name = name;
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

}

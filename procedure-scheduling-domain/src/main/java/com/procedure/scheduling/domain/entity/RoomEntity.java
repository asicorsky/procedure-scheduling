package com.procedure.scheduling.domain.entity;

import javax.persistence.*;

@Entity
@Table(name = "rooms")
public class RoomEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private RoomEntity() {

	}

	public RoomEntity(String name) {

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

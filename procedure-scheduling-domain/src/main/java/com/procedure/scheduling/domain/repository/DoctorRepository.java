package com.procedure.scheduling.domain.repository;

import com.procedure.scheduling.domain.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {

	Optional<DoctorEntity> findByName(String name);
}

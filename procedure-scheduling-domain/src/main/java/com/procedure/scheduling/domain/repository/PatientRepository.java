package com.procedure.scheduling.domain.repository;

import com.procedure.scheduling.domain.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<PatientEntity, Long> {

	Optional<PatientEntity> findByName(String name);
}

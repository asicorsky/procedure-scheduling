package com.procedure.scheduling.domain.repository;

import com.procedure.scheduling.domain.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<PatientEntity, Long> {

	Optional<PatientEntity> findByName(String name);

	@Query("select p from PatientEntity p where not exists (select 1 from StudyEntity s where s.patient.id = p.id)")
	List<PatientEntity> findAvailablePatients();
}

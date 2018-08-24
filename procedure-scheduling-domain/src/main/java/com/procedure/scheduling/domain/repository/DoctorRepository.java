package com.procedure.scheduling.domain.repository;

import com.procedure.scheduling.domain.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {

	Optional<DoctorEntity> findByName(String name);

	@Query("select d from DoctorEntity d where not exists (select 1 from StudyEntity s where s.doctor.id = d.id)")
	List<DoctorEntity> findAvailableDoctors();
}

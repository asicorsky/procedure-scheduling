package com.procedure.scheduling.domain.repository;

import com.procedure.scheduling.domain.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<PatientEntity, Long> {

}

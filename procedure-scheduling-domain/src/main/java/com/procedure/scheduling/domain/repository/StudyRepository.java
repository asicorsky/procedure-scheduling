package com.procedure.scheduling.domain.repository;

import com.procedure.scheduling.domain.entity.StudyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<StudyEntity, Long> {

}

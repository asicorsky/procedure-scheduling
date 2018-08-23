package com.procedure.scheduling.domain.repository;

import com.procedure.scheduling.domain.entity.RoomEntity;
import com.procedure.scheduling.domain.entity.StudyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface StudyRepository extends JpaRepository<StudyEntity, Long> {

	@Query("select se from StudyEntity se where se.plannedStartTime <= :to and (se.estimatedEndTime is null or se.estimatedEndTime >= :from) and se.room = :room")
	List<StudyEntity> findOverlappedStudies(@Param("from") Date from, @Param("to") Date to, @Param("room") RoomEntity room);

	Optional<StudyEntity> findById(long id);
}

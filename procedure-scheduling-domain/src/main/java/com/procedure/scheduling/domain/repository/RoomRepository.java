package com.procedure.scheduling.domain.repository;

import com.procedure.scheduling.domain.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<RoomEntity, Long> {

	Optional<RoomEntity> findByName(String name);
}

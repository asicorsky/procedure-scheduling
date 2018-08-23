package com.procedure.scheduling.service.room.impl;

import com.procedure.scheduling.domain.entity.RoomEntity;
import com.procedure.scheduling.domain.exceptions.EntityNotFoundException;
import com.procedure.scheduling.domain.repository.RoomRepository;
import com.procedure.scheduling.dto.room.RoomDto;
import com.procedure.scheduling.service.mapper.DtoMapper;
import com.procedure.scheduling.service.mapper.EntityMapper;
import com.procedure.scheduling.service.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

	private final RoomRepository roomRepository;

	@Autowired
	public RoomServiceImpl(RoomRepository roomRepository) {

		this.roomRepository = roomRepository;
	}

	@Override
	public RoomDto addRoom(RoomDto room) {

		RoomEntity entity = EntityMapper.toRoomEntity(room.getName());
		roomRepository.save(entity);
		return DtoMapper.toRoomDto(entity);
	}

	@Override
	public RoomDto getRoom(long id) {

		RoomEntity entity = roomRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		return DtoMapper.toRoomDto(entity);
	}

	@Override
	public List<RoomDto> getRooms() {

		return roomRepository.findAll().stream().map(DtoMapper::toRoomDto).collect(Collectors.toList());
	}
}

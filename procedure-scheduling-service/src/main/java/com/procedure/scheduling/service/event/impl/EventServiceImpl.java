package com.procedure.scheduling.service.event.impl;

import com.procedure.scheduling.common.utils.DateUtils;
import com.procedure.scheduling.dto.schedule.EventDto;
import com.procedure.scheduling.dto.schedule.EventRowDto;
import com.procedure.scheduling.dto.study.StudyDto;
import com.procedure.scheduling.service.event.EventService;
import com.procedure.scheduling.service.room.RoomService;
import com.procedure.scheduling.service.study.StudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

	private final RoomService roomService;
	private final StudyService studyService;

	@Autowired
	public EventServiceImpl(RoomService roomService, StudyService studyService) {

		this.roomService = roomService;
		this.studyService = studyService;
	}

	@Override
	public List<EventRowDto> getEventsForToday() {

		Date now = DateUtils.now();
		Date from = DateUtils.toStartOfDay(now);
		Date to = DateUtils.toEndOfDay(now);

		var rooms = roomService.getRooms();
		return rooms.stream().map(room -> {

			var studies = studyService.getStudiesForRoom(from, to, room);
			var events = studies.stream()
					.map(study -> new EventDto(study.getId(), study.getDescription(), study.getStatus(), study.getPlannedStartTime(), study.getEstimatedEndTime(),
							study.getDoctor(), study.getPatient())).collect(Collectors.toList());

			return new EventRowDto(room, events);
		}).collect(Collectors.toList());
	}

	@Override
	public EventDto getEvent(long id) {

		StudyDto study = studyService.getStudy(id);
		return new EventDto(study.getId(), study.getDescription(), study.getStatus(), study.getPlannedStartTime(), study.getEstimatedEndTime(), study.getDoctor(),
				study.getPatient());
	}
}

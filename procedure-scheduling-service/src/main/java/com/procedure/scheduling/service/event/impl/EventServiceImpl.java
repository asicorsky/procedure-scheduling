package com.procedure.scheduling.service.event.impl;

import com.procedure.scheduling.common.utils.DateUtils;
import com.procedure.scheduling.dto.doctor.DoctorDto;
import com.procedure.scheduling.dto.patient.PatientDto;
import com.procedure.scheduling.dto.room.RoomDto;
import com.procedure.scheduling.dto.schedule.EventDto;
import com.procedure.scheduling.dto.schedule.EventRowDto;
import com.procedure.scheduling.dto.study.Status;
import com.procedure.scheduling.dto.study.StudyDto;
import com.procedure.scheduling.service.event.EventService;
import com.procedure.scheduling.service.exceptions.EventValidationException;
import com.procedure.scheduling.service.room.RoomService;
import com.procedure.scheduling.service.study.StudyService;
import com.procedure.scheduling.service.websocket.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

	private final RoomService roomService;
	private final StudyService studyService;
	private final WebSocketService webSocketService;

	@Autowired
	public EventServiceImpl(RoomService roomService, StudyService studyService, WebSocketService webSocketService) {

		this.roomService = roomService;
		this.studyService = studyService;
		this.webSocketService = webSocketService;
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

	@Override
	public EventDto addEvent(EventDto event, long roomId) {

		validate(event);
		RoomDto room = roomService.getRoom(roomId);

		StudyDto study = studyService.addStudy(
				new StudyDto(null, event.getDescription(), event.getPatient(), room, event.getDoctor(), Status.None, event.getPlannedStartTime(), event.getEstimatedEndTime()));
		// broadcast new event to all users
		studyService.checkStatusesForToday();
		webSocketService.roomsUpdate();
		return getEvent(study.getId());
	}

	@Override
	public EventDto changeEvent(EventDto event, long roomId) {

		validate(event);
		RoomDto room = roomService.getRoom(roomId);

		StudyDto study = studyService.changeStudy(
				new StudyDto(event.getId(), event.getDescription(), event.getPatient(), room, event.getDoctor(), event.getStatus(), event.getPlannedStartTime(),
						event.getEstimatedEndTime()));
		//broadcast update to all users
		studyService.checkStatusesForToday();
		webSocketService.roomsUpdate();
		return getEvent(study.getId());
	}

	private void validate(EventDto event) {

		String description = event.getDescription();
		if (Objects.isNull(description) || description.isEmpty()) {
			throw new EventValidationException("Description is null or empty");
		}
		DoctorDto doctor = event.getDoctor();
		if (Objects.isNull(doctor)) {
			throw new EventValidationException("Doctor is null");
		}
		PatientDto patient = event.getPatient();
		if (Objects.isNull(patient)) {
			throw new EventValidationException("Patient is null");
		}
		Date plannedStartTime = event.getPlannedStartTime();
		if (Objects.isNull(plannedStartTime)) {
			throw new EventValidationException("Start time is null");
		}
		Date estimatedEndTime = event.getEstimatedEndTime();
		if (Objects.nonNull(estimatedEndTime) && estimatedEndTime.before(plannedStartTime)) {
			throw new EventValidationException("Start time is after end time");
		}

	}
}

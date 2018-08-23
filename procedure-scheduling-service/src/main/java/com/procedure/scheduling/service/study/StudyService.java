package com.procedure.scheduling.service.study;

import com.procedure.scheduling.dto.room.RoomDto;
import com.procedure.scheduling.dto.study.StudyDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
public interface StudyService {

	StudyDto addStudy(StudyDto study);

	List<StudyDto> getStudies();

	List<StudyDto> getStudiesForRoom(Date from, Date to, RoomDto rom);

	boolean isOverlapped(Date startDate, Date endDate, RoomDto room);

	StudyDto getStudy(long id);
}

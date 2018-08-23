package com.procedure.scheduling.service.study;

import com.procedure.scheduling.dto.room.RoomDto;
import com.procedure.scheduling.dto.study.Status;
import com.procedure.scheduling.dto.study.StudyDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
public interface StudyService {

	void addStudy(StudyDto study);

	void changeStatus(Status status, long id);

	void changeStudy(StudyDto study);

	List<StudyDto> getStudiesForRoom(Date from, Date to, RoomDto rom);

	List<StudyDto> getStudies(Date from, Date to);

	boolean isOverlapped(Date startDate, Date endDate, RoomDto room);

	StudyDto getStudy(long id);
}

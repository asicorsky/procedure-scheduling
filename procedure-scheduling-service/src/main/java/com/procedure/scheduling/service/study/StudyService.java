package com.procedure.scheduling.service.study;

import com.procedure.scheduling.dto.study.StudyDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface StudyService {

	StudyDto addStudy(StudyDto study);

	List<StudyDto> getStudies();
}

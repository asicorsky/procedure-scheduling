package com.procedure.scheduling.service.event;

import com.procedure.scheduling.dto.schedule.EventRowDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface EventService {

	List<EventRowDto> getEventsForToday();
}

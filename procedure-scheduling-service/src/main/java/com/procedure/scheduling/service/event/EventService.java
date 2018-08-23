package com.procedure.scheduling.service.event;

import com.procedure.scheduling.dto.schedule.EventDto;
import com.procedure.scheduling.dto.schedule.EventRowDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * High-level spring service which operate with some low-levels services
 */
@Transactional
public interface EventService {

	List<EventRowDto> getEventsForToday();

	EventDto getEvent(long id);
}

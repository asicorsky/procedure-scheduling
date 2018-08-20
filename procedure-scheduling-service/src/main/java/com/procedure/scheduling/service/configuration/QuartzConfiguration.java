package com.procedure.scheduling.service.configuration;

import com.procedure.scheduling.service.job.RoomsUpdateJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfiguration {

	@Bean
	public JobDetail roomsUpdateJobDetail() {

		return JobBuilder.newJob(RoomsUpdateJob.class).storeDurably().build();
	}

	@Bean
	public Trigger roomsUpdateTrigger() {

		SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(30).repeatForever();
		return TriggerBuilder.newTrigger().forJob(roomsUpdateJobDetail()).withSchedule(scheduleBuilder).build();
	}
}

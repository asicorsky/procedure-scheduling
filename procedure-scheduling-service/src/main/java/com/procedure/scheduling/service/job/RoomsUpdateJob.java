package com.procedure.scheduling.service.job;

import com.procedure.scheduling.service.study.StudyService;
import com.procedure.scheduling.service.websocket.WebSocketService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class RoomsUpdateJob extends QuartzJobBean {

	private WebSocketService webSocketService;

	private StudyService studyService;

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

		studyService.checkStatusesForToday();
		webSocketService.roomsUpdate();
	}

	//cannot autowire via constructor because of quartz job
	@Autowired
	public void setWebSocketService(WebSocketService webSocketService) {

		this.webSocketService = webSocketService;
	}

	@Autowired
	public void setStudyService(StudyService studyService) {

		this.studyService = studyService;
	}
}

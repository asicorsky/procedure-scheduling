package com.procedure.scheduling.service.job;

import com.procedure.scheduling.common.utils.DateUtils;
import com.procedure.scheduling.dto.study.Status;
import com.procedure.scheduling.service.study.StudyService;
import com.procedure.scheduling.service.websocket.WebSocketService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Component
public class RoomsUpdateJob extends QuartzJobBean {

	private WebSocketService webSocketService;

	private StudyService studyService;

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

		final Date now = DateUtils.now();
		final Date from = DateUtils.toStartOfDay(now);
		final Date to = DateUtils.toEndOfDay(now);
		// for real world most of current logic should processed at db side
		var studies = studyService.getStudies(from, to);
		studies.forEach(study -> {
			Date startTime = study.getPlannedStartTime();
			Date endTime = study.getEstimatedEndTime();
			Status status = study.getStatus();
			if ((Objects.isNull(endTime) || endTime.after(now)) && startTime.before(now) && !Status.InProgress.equals(status)) {
				studyService.changeStatus(Status.InProgress, study.getId());
			} else if (Objects.nonNull(endTime) && now.after(endTime) && !Status.Finished.equals(status)) {
				studyService.changeStatus(Status.Finished, study.getId());
			} else if (now.before(startTime) && !Status.Planned.equals(status)) {
				studyService.changeStatus(Status.Planned, study.getId());
			}
		});

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

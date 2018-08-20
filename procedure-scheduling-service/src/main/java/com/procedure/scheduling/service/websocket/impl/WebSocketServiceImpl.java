package com.procedure.scheduling.service.websocket.impl;

import com.procedure.scheduling.dto.websocket.WebSocketEvent;
import com.procedure.scheduling.service.websocket.WebSocketNavigation;
import com.procedure.scheduling.service.websocket.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketServiceImpl implements WebSocketService {

	private final SimpMessagingTemplate messagingTemplate;

	@Autowired
	public WebSocketServiceImpl(SimpMessagingTemplate messagingTemplate) {

		this.messagingTemplate = messagingTemplate;
	}

	@Override
	public void roomsUpdate() {

		messagingTemplate.convertAndSend(WebSocketNavigation.COMMON, WebSocketEvent.ROOMS_UPDATE);
	}
}

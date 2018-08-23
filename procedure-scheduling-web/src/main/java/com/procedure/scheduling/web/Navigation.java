package com.procedure.scheduling.web;

public interface Navigation {

	String ROOT = "/";
	String INDEX = "/index";
	String INDEX_HTML = "index.html";

	String PATIENT = "/patient";
	String ROOM = "/room";
	String EVENT = "/event";
	String DOCTOR = "/doctor";

	String ID_PATH_PARAM = "id";
	String ROOM_ID_PATH_PARAM = "roomId";

	String NEW = "/new";
	String LOAD = "/load";
	String MODIFY = "/modify";
	String ALL = "/all";
	String TODAY = "/today";
	String AVAILABLE = "/available";
	String ID = "/{" + ID_PATH_PARAM + "}";

	String LOAD_ALL = LOAD + ALL;
	String LOAD_TODAY = LOAD + TODAY;
	String LOAD_AVAILABLE = LOAD + AVAILABLE;
	String LOAD_BY_ID = LOAD + ID;
	String NEW_EVENT_FOR_ROOM = "/{" + ROOM_ID_PATH_PARAM + "}" + NEW;
	String MODIFY_EVENT_FOR_ROOM = "/{" + ROOM_ID_PATH_PARAM + "}" + MODIFY;

	String[] STATIC_RESOURCES = {"/img/**", "/css/**", "/js/**"};
}

package com.procedure.scheduling.web;

public interface Navigation {

	String ROOT = "/";
	String INDEX = "/index";
	String INDEX_HTML = "index.html";

	String PATIENT = "/patient";
	String ROOM = "/room";
	String STUDY = "/study";
	String EVENT = "/event";

	String ID_PATH_PARAM = "id";

	String NEW = "/new";
	String LOAD = "/load";
	String ALL = "/all";
	String TODAY = "/today";
	String AVAILABLE = "/available";
	String ID = "/{" + ID_PATH_PARAM + "}";

	String LOAD_ALL = LOAD + ALL;
	String LOAD_TODAY = LOAD + TODAY;
	String LOAD_AVAILABLE = LOAD + AVAILABLE;
	String LOAD_BY_ID = LOAD + ID;

	String[] STATIC_RESOURCES = {"/img/**", "/css/**", "/js/**"};
}

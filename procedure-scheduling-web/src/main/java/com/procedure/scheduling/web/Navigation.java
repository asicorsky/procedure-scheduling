package com.procedure.scheduling.web;

public interface Navigation {

	String ROOT = "/";
	String INDEX = "/index";
	String INDEX_HTML = "index.html";

	String PATIENT = "/patient";
	String ROOM = "/room";
	String STUDY = "/study";

	String NEW = "/new";
	String LOAD = "/load";
	String ALL = "/all";

	String LOAD_ALL = LOAD + ALL;

	String[] STATIC_RESOURCES = {"/img/**", "/css/**", "/js/**"};
}

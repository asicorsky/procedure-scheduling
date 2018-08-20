package com.procedure.scheduling.web;

public interface Navigation {

	String ROOT = "/";
	String INDEX = "/index";
	String INDEX_HTML = "index.html";

	String PATIENT = "/patient";
	String NEW = "/new";

	String[] STATIC_RESOURCES = {"/img/**", "/css/**", "/js/**"};
}

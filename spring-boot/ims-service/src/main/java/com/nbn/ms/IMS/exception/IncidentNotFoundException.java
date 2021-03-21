package com.nbn.ms.IMS.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class IncidentNotFoundException extends RuntimeException {

	public IncidentNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}


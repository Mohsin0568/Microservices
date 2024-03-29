package com.systa.licencing.exceptions;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.http.HttpStatus;

public class RestErrorList extends ArrayList<ErrorMessage>{
    
    /** Generated Serial Version*/
	private static final long serialVersionUID = -721424777198115589L;
	private HttpStatus status;

	public RestErrorList(HttpStatus status, ErrorMessage... errors) {
		this(status.value(), errors);
	}

	public RestErrorList(int status, ErrorMessage... errors) {
		super();
		this.status = HttpStatus.valueOf(status);
		addAll(Arrays.asList(errors));
	}

	/**
	 * @return the status
	 */
	public HttpStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(HttpStatus status) {
		this.status = status;
	}

}

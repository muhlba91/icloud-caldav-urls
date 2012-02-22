package com.niftyside.icloud.calendars.exceptions;

/**
 * A request exception when building requests.
 * 
 * @author Daniel Muehlbachler
 * @copyright 2012 Daniel Muehlbachler
 * 
 * @see {@link http://icloud.niftyside.com}
 * 
 * @version 1.0
 * 
 */
public class RequestException extends Exception {
	/* * * * * Variables * * * * */

	private static final long serialVersionUID = -2263337562883731525L;

	/* * * * * Constructor * * * * */

	/**
	 * Creates a new request exception.
	 * 
	 * @param message
	 *            the error message
	 * 
	 * @since 1.0
	 */
	public RequestException(String message) {
		super(message);
	}

	/* * * * * Methods * * * * */

	@Override
	public String getMessage() {
		return "Request: " + super.getMessage();
	}
}

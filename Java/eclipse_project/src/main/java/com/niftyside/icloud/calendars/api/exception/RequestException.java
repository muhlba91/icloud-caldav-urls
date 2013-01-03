package com.niftyside.icloud.calendars.api.exception;

/**
 * A request exception when building requests.
 * 
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 * 
 * @see http://icloud.niftyside.com
 * 
 * @version 1.2.1
 * 
 */
public class RequestException extends DefaultException {
	/* * * * * Variables * * * * */

	private static final long serialVersionUID = -2263337562883731525L;

	/* * * * * Constructor * * * * */

	/**
	 * Creates a new request exception.
	 * 
	 * @param message
	 *            the error message
	 * @param originalException
	 *            the originally thrown exception
	 * 
	 * @since 1.2.1
	 */
	public RequestException(final String message, final Exception originalException) {
		super(message, originalException);
	}

	/* * * * * Methods * * * * */

	@Override
	public String getMessage() {
		return "Request: " + super.getMessage();
	}
}

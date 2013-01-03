package com.niftyside.icloud.calendars.api.exception;

/**
 * A calendar exception for calendar errors.
 * 
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 * 
 * @see http://icloud.niftyside.com
 * 
 * @version 1.2.1
 * 
 */
public class CalendarException extends DefaultException {
	/* * * * * Variables * * * * */

	private static final long serialVersionUID = -5870009742191195836L;

	/* * * * * Constructor * * * * */

	/**
	 * Creates a new calendar exception.
	 * 
	 * @param message
	 *            the error message
	 * @param originalException
	 *            the originally thrown exception
	 * 
	 * @since 1.2.1
	 */
	public CalendarException(final String message, final Exception originalException) {
		super(message, originalException);
	}

	/* * * * * Methods * * * * */

	@Override
	public String getMessage() {
		return "Calendar: " + super.getMessage();
	}
}

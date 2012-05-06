package com.niftyside.icloud.calendars.api.exception;

import com.niftyside.icloud.calendars.api.model.ExceptionModel;

/**
 * A calendat exception for calendar errors.
 * 
 * @author Daniel Muehlbachler
 * @copyright 2012 Daniel Muehlbachler
 * 
 * @see http://icloud.niftyside.com
 * 
 * @version 1.0
 * 
 */
public class CalendarException extends Exception implements ExceptionModel {
	/* * * * * Variables * * * * */

	private static final long serialVersionUID = -6213845791976672460L;
	/** The originally thrown exception. */
	private final Exception originalException;

	/* * * * * Constructor * * * * */

	/**
	 * Creates a new calendat exception.
	 * 
	 * @param message
	 *            the error message
	 * @param originalException
	 *            the originally thrown exception
	 * 
	 * @since 1.1
	 */
	public CalendarException(final String message,
			final Exception originalException) {
		super(message);

		this.originalException = originalException;
	}

	/* * * * * Methods * * * * */

	@Override
	public Exception getOriginalException() {
		return originalException;
	}

	@Override
	public String getMessage() {
		return "Calendar: " + super.getMessage();
	}
}

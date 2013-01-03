package com.niftyside.icloud.calendars.api.exception;

import com.niftyside.icloud.calendars.api.model.ExceptionModel;

/**
 * The default super class for all {@link ExceptionModel} implementations
 * 
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 * 
 * @see http://icloud.niftyside.com
 * 
 * @version 1.2.1
 * 
 */
public class DefaultException extends Exception implements ExceptionModel {
	/* * * * * Variables * * * * */

	private static final long serialVersionUID = 587416421673757462L;
	/** The originally thrown exception. */
	private final Exception originalException;

	/* * * * * Constructor * * * * */

	/**
	 * Creates a new XML exception.
	 * 
	 * @param message
	 *            the error message
	 * @param originalException
	 *            the originally thrown exception
	 * 
	 * @since 1.2.1
	 */
	public DefaultException(final String message, final Exception originalException) {
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
		return "Default: " + super.getMessage();
	}
}

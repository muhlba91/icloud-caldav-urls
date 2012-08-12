package com.niftyside.icloud.calendars.api.exception;

import com.niftyside.icloud.calendars.api.model.ExceptionModel;

/**
 * A server exception for all errors regarding sending requests and retrieving
 * responses from a server.
 * 
 * @author Daniel Muehlbachler
 * @copyright 2012 Daniel Muehlbachler
 * 
 * @see http://icloud.niftyside.com
 * 
 * @version 1.0
 * 
 */
public class ServerException extends Exception implements ExceptionModel {
	/* * * * * Variables * * * * */

	private static final long serialVersionUID = -6305567538168099993L;
	/** The originally thrown exception. */
	private final Exception originalException;

	/* * * * * Constructor * * * * */

	/**
	 * Creates a new server exception.
	 * 
	 * @param message
	 *            the error message
	 * @param originalException
	 *            the originally thrown exception
	 * 
	 * @since 1.1
	 */
	public ServerException(final String message,
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
		return "Server: " + super.getMessage();
	}
}

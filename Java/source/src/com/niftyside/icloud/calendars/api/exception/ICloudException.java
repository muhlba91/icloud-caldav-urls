package com.niftyside.icloud.calendars.api.exception;

import com.niftyside.icloud.calendars.api.model.ExceptionModel;

/**
 * A factory exception for the client factories.
 * 
 * @author Daniel Muehlbachler
 * @copyright 2012 Daniel Muehlbachler
 * 
 * @see http://icloud.niftyside.com
 * 
 * @version 1.1
 * 
 */
public class ICloudException extends Exception implements ExceptionModel {
	/* * * * * Variables * * * * */

	private static final long serialVersionUID = -6213845791976672460L;
	/** The originally thrown exception. */
	private final Exception originalException;

	/* * * * * Constructor * * * * */

	/**
	 * Creates a new iCloud exception.
	 * 
	 * @param message
	 *            the error message
	 * @param originalException
	 *            the originally thrown exception
	 * 
	 * @since 1.1
	 */
	public ICloudException(final String message,
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
		return "iCloud: " + super.getMessage();
	}
}

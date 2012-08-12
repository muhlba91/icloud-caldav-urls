package com.niftyside.icloud.calendars.api.exception;

import com.niftyside.icloud.calendars.api.model.ExceptionModel;

/**
 * A XML exception for the XML reader.
 * 
 * @author Daniel Muehlbachler
 * @copyright 2012 Daniel Muehlbachler
 * 
 * @see http://icloud.niftyside.com
 * 
 * @version 1.0
 * 
 */
public class XMLException extends Exception implements ExceptionModel {
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
	 * @since 1.1
	 */
	public XMLException(final String message, final Exception originalException) {
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
		return "XML: " + super.getMessage();
	}
}

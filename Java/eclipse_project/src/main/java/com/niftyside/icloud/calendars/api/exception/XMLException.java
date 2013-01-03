package com.niftyside.icloud.calendars.api.exception;

/**
 * A XML exception for the XML reader.
 * 
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 * 
 * @see http://icloud.niftyside.com
 * 
 * @version 1.2.1
 * 
 */
public class XMLException extends DefaultException {
	/* * * * * Variables * * * * */

	private static final long serialVersionUID = 587416421673757462L;

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
	public XMLException(final String message, final Exception originalException) {
		super(message, originalException);
	}

	/* * * * * Methods * * * * */

	@Override
	public String getMessage() {
		return "XML: " + super.getMessage();
	}
}

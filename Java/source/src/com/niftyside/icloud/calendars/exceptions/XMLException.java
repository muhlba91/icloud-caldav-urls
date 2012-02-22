package com.niftyside.icloud.calendars.exceptions;

/**
 * A XML exception for the XML reader.
 * 
 * @author Daniel Muehlbachler
 * @copyright 2012 Daniel Muehlbachler
 * 
 * @see {@link http://icloud.niftyside.com}
 * 
 * @version 1.0
 * 
 */
public class XMLException extends Exception {
	/* * * * * Variables * * * * */

	private static final long serialVersionUID = 587416421673757462L;

	/* * * * * Constructor * * * * */

	/**
	 * Creates a new XML exception.
	 * 
	 * @param message
	 *            the error message
	 * 
	 * @since 1.0
	 */
	public XMLException(String message) {
		super(message);
	}

	/* * * * * Methods * * * * */

	@Override
	public String getMessage() {
		return "XML: " + super.getMessage();
	}
}

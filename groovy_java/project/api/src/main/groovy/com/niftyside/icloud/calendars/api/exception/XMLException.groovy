package com.niftyside.icloud.calendars.api.exception

/**
 * XML exception.
 *
 * Project: iCloud calendars
 * User: daniel
 * Date: 23.09.13
 * Time: 16:32
 *
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 *
 * @version 2.0.0
 */
class XMLException extends DefaultException {
	/* * * * * Constructor * * * * */

	/**
	 * Creates a new XML exception.
	 *
	 * @param message
	 * 		the message
	 * @param originalException
	 * 		the original exception
	 *
	 * @since 2.0.0
	 */
	XMLException(String message, Exception originalException) {
		super(message, originalException)
	}

	/* * * * * Methods * * * * */

	@Override
	public String getMessage() {
		"XML: " + super.getMessage()
	}
}

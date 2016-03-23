package com.niftyside.icloud.calendars.api.exception

/**
 * Main exception for the Calendars API.
 *
 * Project: iCloud calendars
 * User: daniel
 * Date: 23.09.13
 * Time: 17:27
 *
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 *
 * @version 2.0.0
 */
class CalendarsException extends DefaultException {
	/* * * * * Constructor * * * * */

	/**
	 * Creates a new Calendars exception.
	 *
	 * @param message
	 * 		the message
	 * @param originalException
	 * 		the original exception
	 *
	 * @since 2.0.0
	 */
	CalendarsException(String message, Exception originalException) {
		super(message, originalException)
	}

	/* * * * * Methods * * * * */

	@Override
	String getMessage() {
		"Calendars: " + super.message
	}
}

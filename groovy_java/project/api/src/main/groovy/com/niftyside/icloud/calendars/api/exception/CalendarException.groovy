package com.niftyside.icloud.calendars.api.exception

/**
 * Calendar exception for occuring errors within a calendar.
 *
 * Project: iCloud calendars
 * User: daniel
 * Date: 23.09.13
 * Time: 17:03
 *
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 *
 * @version 2.0.0
 */
class CalendarException extends DefaultException {
	/* * * * * Constructor * * * * */

	/**
	 * Creates a new calendar exception.
	 *
	 * @param message
	 * 		the message
	 * @param originalException
	 * 		the original exception
	 *
	 * @since 2.0.0
	 */
	CalendarException(String message, Exception originalException) {
		super(message, originalException)
	}

	/* * * * * Methods * * * * */

	@Override
	String getMessage() {
		"Calendar: " + super.message
	}
}

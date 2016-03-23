package com.niftyside.icloud.calendars.api.exception

/**
 * Exception for errors during building the request.
 *
 * Project: iCloud calendars
 * User: daniel
 * Date: 23.09.13
 * Time: 16:40
 *
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 *
 * @version 2.0.0
 */
class RequestException extends DefaultException {
	/* * * * * Constructor * * * * */

	/**
	 * Creates a new request exception.
	 *
	 * @param message
	 * 		the message
	 * @param originalException
	 * 		the original exception
	 *
	 * @since 2.0.0
	 */
	RequestException(String message, Exception originalException) {
		super(message, originalException)
	}

	/* * * * * Methods * * * * */

	@Override
	String getMessage() {
		"Request: " + super.message
	}
}

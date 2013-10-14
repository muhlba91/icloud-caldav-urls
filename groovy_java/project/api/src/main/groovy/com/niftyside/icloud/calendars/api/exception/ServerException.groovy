package com.niftyside.icloud.calendars.api.exception

/**
 * Exception for server communication errors.
 *
 * Project: iCloud calendars
 * User: daniel
 * Date: 23.09.13
 * Time: 17:34
 *
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 *
 * @version 2.0.0
 */
class ServerException extends DefaultException {
	/* * * * * Constructor * * * * */

	/**
	 * Creates a new server exception.
	 *
	 * @param message
	 * 		the message
	 * @param originalException
	 * 		the original exception
	 *
	 * @since 2.0.0
	 */
	ServerException(String message, Exception originalException) {
		super(message, originalException)
	}

	/* * * * * Methods * * * * */

	@Override
	public String getMessage() {
		"Server: " + super.getMessage()
	}
}

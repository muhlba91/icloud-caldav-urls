package com.niftyside.icloud.calendars.api.exception

/**
 * Client factory exception.
 *
 * Project: iCloud calendars
 * User: daniel
 * Date: 18.09.13
 * Time: 22:36
 *
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 *
 * @version 2.0.0
 */
class FactoryException extends DefaultException {
	/* * * * * Constructor * * * * */

	/**
	 * Creates a new factory exception.
	 *
	 * @param message
	 * 		the message
	 * @param originalException
	 * 		the original exception
	 *
	 * @since 2.0.0
	 */
	FactoryException(String message, Exception originalException) {
		super(message, originalException)
	}

	/* * * * * Methods * * * * */

	@Override
	public String getMessage() {
		"Factory: " + super.getMessage()
	}
}

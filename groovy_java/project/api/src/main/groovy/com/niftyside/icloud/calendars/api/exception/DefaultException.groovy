package com.niftyside.icloud.calendars.api.exception

/**
 * Default super class for all exceptions.
 *
 * Project: iCloud calendars
 * User: daniel
 * Date: 18.09.13
 * Time: 22:37
 *
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 *
 * @version 2.0.0
 */
abstract class DefaultException extends Exception {
	/* * * * * Variables * * * * */

	def originalException

	/* * * * * Constructor * * * * */

	/**
	 * Creates a new default exception.
	 *
	 * @param message
	 * 		the message
	 * @param originalException
	 * 		the original exception
	 *
	 * @since 2.0.0
	 */
	DefaultException(String message, Exception originalException) {
		super(message)

		this.originalException = originalException
	}

	/* * * * * Methods * * * * */

	@Override
	public String getMessage() {
		"Default: " + super.getMessage()
	}
}

package com.niftyside.icloud.calendars.api.exception;

/**
 * A server exception for all errors regarding sending requests and retrieving
 * responses from a server.
 * 
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 * 
 * @see http://icloud.niftyside.com
 * 
 * @version 1.2.1
 * 
 */
public class ServerException extends DefaultException {
	/* * * * * Variables * * * * */

	private static final long serialVersionUID = -6305567538168099993L;

	/* * * * * Constructor * * * * */

	/**
	 * Creates a new server exception.
	 * 
	 * @param message
	 *            the error message
	 * @param originalException
	 *            the originally thrown exception
	 * 
	 * @since 1.2.1
	 */
	public ServerException(final String message, final Exception originalException) {
		super(message, originalException);
	}

	/* * * * * Methods * * * * */

	@Override
	public String getMessage() {
		return "Server: " + super.getMessage();
	}
}

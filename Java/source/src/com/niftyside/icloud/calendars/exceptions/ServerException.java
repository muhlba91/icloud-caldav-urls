package com.niftyside.icloud.calendars.exceptions;

/**
 * A server exception for all errors regarding sending requests and retrieving
 * responses from a server.
 * 
 * @author Daniel Muehlbachler
 * @copyright 2012 Daniel Muehlbachler
 * 
 * @see {@link http://icloud.niftyside.com}
 * 
 * @version 1.0
 * 
 */
public class ServerException extends Exception {
	/* * * * * Variables * * * * */

	private static final long serialVersionUID = -6305567538168099993L;

	/* * * * * Constructor * * * * */

	/**
	 * Creates a new server exception.
	 * 
	 * @param message
	 *            the error message
	 * 
	 * @since 1.0
	 */
	public ServerException(String message) {
		super(message);
	}

	/* * * * * Methods * * * * */

	@Override
	public String getMessage() {
		return "Server: " + super.getMessage();
	}
}

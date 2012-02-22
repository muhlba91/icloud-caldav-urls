package com.niftyside.icloud.calendars.exceptions;

/**
 * A factory exception for the client factories.
 * 
 * @author Daniel Muehlbachler
 * @copyright 2012 Daniel Muehlbachler
 * 
 * @see {@link http://icloud.niftyside.com}
 * 
 * @version 1.0
 * 
 */
public class FactoryException extends Exception {
	/* * * * * Variables * * * * */

	private static final long serialVersionUID = -6213845791976672460L;

	/* * * * * Constructor * * * * */

	/**
	 * Creates a new factory exception.
	 * 
	 * @param message
	 *            the error message
	 * 
	 * @since 1.0
	 */
	public FactoryException(String message) {
		super(message);
	}

	/* * * * * Methods * * * * */

	@Override
	public String getMessage() {
		return "Factory: " + super.getMessage();
	}
}

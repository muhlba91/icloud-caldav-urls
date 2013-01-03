package com.niftyside.icloud.calendars.api.exception;

/**
 * A factory exception for the client factories.
 * 
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 * 
 * @see http://icloud.niftyside.com
 * 
 * @version 1.2.1
 * 
 */
public class ICloudException extends DefaultException {
	/* * * * * Variables * * * * */

	private static final long serialVersionUID = -1272025484035504297L;

	/* * * * * Constructor * * * * */

	/**
	 * Creates a new iCloud exception.
	 * 
	 * @param message
	 *            the error message
	 * @param originalException
	 *            the originally thrown exception
	 * 
	 * @since 1.2.1
	 */
	public ICloudException(final String message, final Exception originalException) {
		super(message, originalException);
	}

	/* * * * * Methods * * * * */

	@Override
	public String getMessage() {
		return "iCloud: " + super.getMessage();
	}
}

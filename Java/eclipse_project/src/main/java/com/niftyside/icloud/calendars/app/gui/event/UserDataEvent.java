package com.niftyside.icloud.calendars.app.gui.event;

import com.niftyside.icloud.calendars.api.exception.ICloudException;
import com.niftyside.icloud.calendars.api.model.UserData;

/**
 * Event that gets fired when new {@link UserData} must be displayed.
 * 
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 * 
 * @see http://icloud.niftyside.com
 * 
 * @version 1.1
 * 
 */
public class UserDataEvent {
	/* * * * * Variables * * * * */

	/** The user data. */
	private final UserData userData;
	/** The exception, if any. */
	private final ICloudException exception;

	/* * * * * Constructor * * * * */

	/**
	 * Creates a new user data event.
	 * 
	 * @param userData
	 *            the data to set
	 * @param exception
	 *            the exception to set
	 * 
	 * @since 1.1
	 */
	public UserDataEvent(final UserData userData, final ICloudException exception) {
		this.userData = userData;
		this.exception = exception;
	}

	/* * * * * Methods * * * * */

	/**
	 * Checks if the retrieved user data is valid.
	 * 
	 * @return true - if it's valid; false - otherwise
	 * 
	 * @since 1.1
	 */
	public boolean isValid() {
		return exception == null;
	}

	/**
	 * Gets the saved {@link UserData}.
	 * 
	 * @return the user data
	 * 
	 * @since 1.1
	 */
	public UserData getUserData() {
		return userData;
	}

	/**
	 * Gets the {@link ICloudException}.
	 * 
	 * @return the exception
	 * 
	 * @since 1.1
	 */
	public ICloudException getException() {
		return exception;
	}
}

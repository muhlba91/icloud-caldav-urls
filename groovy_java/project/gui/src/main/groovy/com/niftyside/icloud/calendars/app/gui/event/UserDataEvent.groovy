package com.niftyside.icloud.calendars.app.gui.event

import com.niftyside.icloud.calendars.api.exception.CalendarsException
import com.niftyside.icloud.calendars.api.model.UserData

/**
 * Event that gets fired when new {@link UserData} is available.
 *
 * Project: iCloud calendars
 * User: daniel
 * Date: 12.10.13
 * Time: 17:55
 *
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 *
 * @version 2.0.0
 */
class UserDataEvent {
	/* * * * * Variables * * * * */

	public final def userData
	public final def exception

	/* * * * * Constructor * * * * */

	/**
	 * Creates a new user data event.
	 *
	 * @param userData
	 *            the data to set
	 * @param exception
	 *            the exception to set
	 *
	 * @since 2.0.0
	 */
	UserDataEvent(UserData userData, CalendarsException exception) {
		this.userData = userData
		this.exception = exception
	}

	/* * * * * Methods * * * * */

	/**
	 * Checks if the retrieved user data is valid.
	 *
	 * @return true - if it's valid; false - otherwise
	 *
	 * @since 2.0.0
	 */
	def valid = {->
		exception == null
	}
}

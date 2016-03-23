package com.niftyside.icloud.calendars.app.gui.listener

import com.niftyside.icloud.calendars.app.gui.event.UserDataEvent

/**
 * Interface for listening for new {@link com.niftyside.icloud.calendars.api.model.UserData}.
 *
 * Project: iCloud calendars
 * User: daniel
 * Date: 12.10.13
 * Time: 17:54
 *
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 *
 * @version 2.0.0
 */
interface UserDataListener extends EventListener {
	/**
	 * Called if a {@link UserDataEvent} occurs.
	 *
	 * @param event
	 *            the event
	 *
	 * @since 2.0.0
	 */
	def newUserData(UserDataEvent event)
}

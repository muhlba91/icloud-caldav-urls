package com.niftyside.icloud.calendars.app.gui.listener;

import java.util.EventListener;

import com.niftyside.icloud.calendars.api.model.UserData;
import com.niftyside.icloud.calendars.app.gui.event.UserDataEvent;

/**
 * Interface for {@link UserData} listener.
 * 
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 * 
 * @see http://icloud.niftyside.com
 * 
 * @version 1.1
 * 
 */
public interface UserDataListener extends EventListener {
	/**
	 * Called if a {@link UserDataEvent} occurs.
	 * 
	 * @param event
	 *            the event
	 * 
	 * @since 1.1
	 */
	void newUserData(UserDataEvent event);
}

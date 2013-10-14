package com.niftyside.icloud.calendars.app.gui.listener

import com.niftyside.icloud.calendars.app.gui.event.CredentialEvent

/**
 * Interface for listening for sent credentials.
 *
 * Project: iCloud calendars
 * User: daniel
 * Date: 12.10.13
 * Time: 17:51
 *
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 *
 * @version 2.0.0
 */
public interface CredentialListener extends EventListener {
	/**
	 * Called if a {@link CredentialEvent} occurs.
	 *
	 * @param e
	 *            the event
	 *
	 * @since 2.0.0
	 */
	def credentialsSent(CredentialEvent event)
}

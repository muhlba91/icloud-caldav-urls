package com.niftyside.icloud.calendars.app.listeners;

import java.util.EventListener;

import com.niftyside.icloud.calendars.app.events.CredentialEvent;

/**
 * Interface for credential listener.
 * 
 * @author Daniel Muehlbachler
 * @copyright 2012 Daniel Muehlbachler
 * 
 * @see {@link http://icloud.niftyside.com}
 * 
 * @version 1.0
 * 
 */
public interface CredentialListener extends EventListener {
	/**
	 * Called if a {@link CredentialEvent} occurs.
	 * 
	 * @param e
	 *            the event
	 * 
	 * @since 1.0
	 */
	void credentialsSent(CredentialEvent e);
}

package com.niftyside.icloud.calendars.app.gui.event

/**
 * Event that gets fired when credentials are submitted.
 *
 * Project: iCloud calendars
 * User: daniel
 * Date: 12.10.13
 * Time: 17:52
 *
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 *
 * @version 2.0.0
 */
class CredentialEvent {
	/* * * * * Variables * * * * */

	public final appleId
	public final password
	public final server

	/* * * * * Constructor * * * * */

	/**
	 * Creates a new event.
	 *
	 * @param appleId
	 *            the Apple ID
	 * @param password
	 *            the password
	 * @param server
	 *            the server to use
	 *
	 * @since 2.0.0
	 */
	CredentialEvent(String appleId, String password, String server) {
		this.appleId = appleId
		this.password = password
		this.server = server
	}
}

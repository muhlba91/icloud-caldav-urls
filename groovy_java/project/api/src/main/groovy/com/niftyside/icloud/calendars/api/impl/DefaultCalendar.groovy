package com.niftyside.icloud.calendars.api.impl

import com.niftyside.icloud.calendars.api.exception.CalendarException

/**
 * Default calendar implementation.
 *
 * Project: iCloud calendars
 * User: daniel
 * Date: 23.09.13
 * Time: 17:05
 *
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 *
 * @version 2.0.0
 */
class DefaultCalendar implements com.niftyside.icloud.calendars.api.model.Calendar {
	/* * * * * Variables * * * * */

	private static final def UTF8 = "UTF-8"
	private final def name
	private final def href
	private final def server

	/* * * * * Constructor * * * * */

	/**
	 * Creates a new calendar object.
	 *
	 * @param name
	 *            the name
	 * @param href
	 *            the href
	 * @param server
	 *            the server
	 * @throws CalendarException
	 *             if an error encoding the strings occurs
	 *
	 * @since 2.0.0
	 */
	DefaultCalendar(String name, String href, String server) throws CalendarException {
		try {
			this.name = new String(name.getBytes(UTF8), UTF8)
			this.href = new String(href.getBytes(UTF8), UTF8)
			this.server = new String(server.getBytes(UTF8), UTF8)
		} catch(final UnsupportedEncodingException e) {
			throw new CalendarException("Can't encode to UTF-8!", e)
		}
	}

	/* * * * * Methods * * * * */

	@Override
	def getName() {
		name
	}

	@Override
	def getHref() {
		return href
	}

	@Override
	def getURL() {
		"https://" + server + href
	}
}

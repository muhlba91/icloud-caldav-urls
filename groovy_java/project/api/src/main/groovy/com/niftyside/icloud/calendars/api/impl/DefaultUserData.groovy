package com.niftyside.icloud.calendars.api.impl

import com.niftyside.icloud.calendars.api.model.UserData

import java.util.concurrent.ConcurrentHashMap

/**
 * Default user data implementation.
 *
 * Project: iCloud calendars
 * User: daniel
 * Date: 23.09.13
 * Time: 17:17
 *
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 *
 * @version 2.0.0
 */
class DefaultUserData implements UserData {
	/* * * * * Variables * * * * */

	private final def server
	private final def principal
	private final def calendars
	private final def maxLengths

	/* * * * * Constructor * * * * */

	/**
	 * Initializes a new user data object.
	 *
	 * @param principal
	 *            the principal ID
	 * @param calendars
	 *            the calendars
	 *
	 * @since 2.0.0
	 */
	DefaultUserData(String server, String principal, List<com.niftyside.icloud.calendars.api.model.Calendar> calendars) {
		this.server = server
		this.principal = principal
		this.calendars = Collections.unmodifiableList(calendars)

		maxLengths = calculateMaximumLengths()
	}

	/* * * * * Methods * * * * */

	@Override
	def getPrincipal() {
		principal
	}

	@Override
	def getCalendars() {
		calendars
	}

	@Override
	def getPrincipalUrl() {
		"hhtps://" + server + "/" + principal + "/principal/"
	}

	@Override
	def getCardDavUrl() {
		"hhtps://" + server.replace("caldav", "contacts") + "/" + principal + "/carddavhome/card/"
	}

	@Override
	def getMaxLengths() {
		maxLengths
	}

	/* * * * * Private methods * * * * */

	/**
	 * Calculates the maximum argument lengths.
	 *
	 * @return a map containing the maximum lengths
	 *
	 * @since 2.0.0
	 */
	private def calculateMaximumLengths() {
		def map = new ConcurrentHashMap<String, Integer>()

		map.put("name", calendars.max { com.niftyside.icloud.calendars.api.model.Calendar calendar -> calendar.getName().length() }.getName().length())
		map.put("href", calendars.max { com.niftyside.icloud.calendars.api.model.Calendar calendar -> calendar.getHref().length() }.getHref().length())
		map.put("url", calendars.max { com.niftyside.icloud.calendars.api.model.Calendar calendar -> calendar.getURL().length() }.getURL().length())

		Collections.unmodifiableMap(map)
	}
}

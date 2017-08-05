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
 * @copyright 2011-2016 Daniel Muehlbachler
 *
 * @version 2.1.0
 */
class DefaultUserData implements UserData {
	/* * * * * Variables * * * * */

	private final server
	private final principal
	private final calendars
	private final cardDavUrl
	private final maxLengths

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
		this(server, principal, calendars, null)
	}

	/**
	 * Initializes a new user data object.
	 *
	 * @param principal
	 *            the principal ID
	 * @param calendars
	 *            the calendars
	 * @param cardDavUrl
	 *           the CardDAV URL
	 *
	 * @since 2.1.0
	 */
	DefaultUserData(String server, String principal, List<com.niftyside.icloud.calendars.api.model.Calendar> calendars, String cardDavUrl) {
		this.server = server
		this.principal = principal
		this.calendars = Collections.unmodifiableList(calendars)
		this.cardDavUrl = cardDavUrl

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
		"https://" + server + "/" + principal + "/principal/"
	}

	@Override
	def getCardDavUrl() {
		this.cardDavUrl ?: "https://" + server.replace("caldav", "contacts") + "/" + principal + "/carddavhome/"
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
	private calculateMaximumLengths() {
		def map = new ConcurrentHashMap<String, Integer>()

		map.put("name", calendars.max { com.niftyside.icloud.calendars.api.model.Calendar calendar -> calendar.name.length() }.name.length())
		map.put("href", calendars.max { com.niftyside.icloud.calendars.api.model.Calendar calendar -> calendar.href.length() }.href.length())
		map.put("url", calendars.max { com.niftyside.icloud.calendars.api.model.Calendar calendar -> calendar.getURL().length() }.URL.length())

		Collections.unmodifiableMap(map)
	}
}

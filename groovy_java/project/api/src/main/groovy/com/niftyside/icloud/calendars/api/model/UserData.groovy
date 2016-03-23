package com.niftyside.icloud.calendars.api.model

/**
 * Model for user related data.
 *
 * Project: iCloud calendars
 * User: daniel
 * Date: 23.09.13
 * Time: 16:35
 *
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 *
 * @version 2.0.0
 */
interface UserData {
	/**
	 * Gets the principal ID.
	 *
	 * @return the principal ID
	 *
	 * @since 2.0.0
	 */
	def getPrincipal()

	/**
	 * Gets the principal URL.
	 *
	 * @return
	 * the Principal URL
	 *
	 * @since 2.0.0
	 */
	def getPrincipalUrl()

	/**
	 * Gets the CardDAV URL.
	 *
	 * @return
	 * the CardDAV URL
	 *
	 * @since 2.0.0
	 */
	def getCardDavUrl()

	/**
	 * Gets all calendars.
	 *
	 * @return the calendars
	 *
	 * @since 2.0.0
	 */
	def getCalendars()


	/**
	 * Gets the maximum lengths of {@link Calendar} details.
	 *
	 * @return the maximum length of calendar details
	 *
	 * @since 2.0.0
	 */
	def getMaxLengths()
}

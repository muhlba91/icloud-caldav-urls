package com.niftyside.icloud.calendars.api.model

/**
 * Model for an iCloud calendar.
 *
 * Project: iCloud calendars
 * User: daniel
 * Date: 23.09.13
 * Time: 16:33
 *
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 *
 * @version 2.0.0
 */
interface Calendar {
	/**
	 * Gets the name.
	 *
	 * @return the name
	 *
	 * @since 2.0.0
	 */
	def getName()

	/**
	 * Gets the href.
	 *
	 * @return the href
	 *
	 * @since 2.0.0
	 */
	def getHref()

	/**
	 * Gets the calendar URL.
	 *
	 * @return the URL
	 *
	 * @since 2.0.0
	 */
	def getURL()
}

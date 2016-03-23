package com.niftyside.icloud.calendars.api.model

import com.niftyside.icloud.calendars.api.exception.XMLException

/**
 * Reader for reading and parsing a string according to the requested data.
 *
 * Project: iCloud calendars
 * User: daniel
 * Date: 23.09.13
 * Time: 16:29
 *
 * @author Daniel Muehlbachler
 * @copyright 2011-2016 Daniel Muehlbachler
 *
 * @version 2.1.0
 */
interface XMLReader {
	/**
	 * Parses a new XML string.
	 *
	 * @param xml
	 *            the XML to parse
	 * @throws XMLException
	 *             if an error occurs
	 *
	 * @since 2.0.0
	 */
	def setXML(String xml) throws XMLException

	/**
	 * Gets the principal ID.
	 *
	 * @return the principal ID
	 *
	 * @since 2.0.0
	 */
	def getPrincipal()

	/**
	 * Gets the CardDAV URL.
	 *
	 * @return the CardDAV URL
	 *
	 * @since 2.1.0
	 */
	def getCardDavUrl()

	/**
	 * Gets all {@link Calendar}s.
	 *
	 * @param server
	 *            the server
	 * @return all calendar responses
	 *
	 * @since 2.0.0
	 */
	def getCalendars(String server)
}

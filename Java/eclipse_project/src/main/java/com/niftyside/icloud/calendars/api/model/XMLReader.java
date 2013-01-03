package com.niftyside.icloud.calendars.api.model;

import java.util.List;

import com.niftyside.icloud.calendars.api.exception.XMLException;

/**
 * Reads a XML string and parses it according to the requested data.
 * 
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 * 
 * @see http://icloud.niftyside.com
 * 
 * @version 1.1
 * 
 */
public interface XMLReader {
	/**
	 * Parses a new XML string.
	 * 
	 * @param xml
	 *            the XML to parse
	 * @throws XMLException
	 *             if an error occurs
	 * 
	 * @since 1.0
	 */
	void setXML(final String xml) throws XMLException;

	/**
	 * Gets the principal ID.
	 * 
	 * @return the principal ID
	 * 
	 * @since 1.0
	 */
	String getPrincipal();

	/**
	 * Gets all {@link Calendar}s.
	 * 
	 * @param server
	 *            the server
	 * @return all calendar responses
	 * 
	 * @since 1.1
	 */
	List<Calendar> getCalendars(final String server);
}

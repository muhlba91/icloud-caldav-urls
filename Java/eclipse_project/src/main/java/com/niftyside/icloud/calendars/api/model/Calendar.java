package com.niftyside.icloud.calendars.api.model;

/**
 * All needed information about a calendar response.
 * 
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 * 
 * @see http://icloud.niftyside.com
 * 
 * @version 1.1
 * 
 */
public interface Calendar {
	/**
	 * Gets the name.
	 * 
	 * @return the name
	 * 
	 * @since 1.0
	 */
	String getName();

	/**
	 * Gets the href.
	 * 
	 * @return the href
	 * 
	 * @since 1.0
	 */
	String getHref();

	/**
	 * Gets the calendar URL.
	 * 
	 * @return the URL
	 * 
	 * @since 1.0
	 */
	String getURL();
}

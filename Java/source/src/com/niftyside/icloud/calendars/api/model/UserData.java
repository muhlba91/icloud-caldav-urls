package com.niftyside.icloud.calendars.api.model;

import java.util.List;
import java.util.Map;

/**
 * Data object which holds all important information about a user.
 * 
 * @author Daniel Muehlbachler
 * @copyright 2012 Daniel Muehlbachler
 * 
 * @see http://icloud.niftyside.com
 * 
 * @version 1.1
 * 
 */
public interface UserData {
	/**
	 * Gets the principal ID.
	 * 
	 * @return the principal ID
	 * 
	 * @since 1.0
	 */
	String getPrincipal();

	/**
	 * Gets all calendars.
	 * 
	 * @return the calendars
	 * 
	 * @since 1.1
	 */
	List<Calendar> getCalendars();

	/**
	 * Gets the maximum lengths of {@link Calendar} details.
	 * 
	 * @return the maximum length of calendar details
	 * 
	 * @since 1.1
	 */
	Map<String, Integer> getMaxLengths();
}

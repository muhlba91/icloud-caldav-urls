package com.niftyside.icloud.calendars.handlers;

import java.util.List;

/**
 * Data object which holds all important information about a user.
 * 
 * @author Daniel Muehlbachler
 * @copyright 2012 Daniel Muehlbachler
 * 
 * @see {@link http://icloud.niftyside.com}
 * 
 * @version 1.0
 * 
 */
public class Data {
	/* * * * * Variables * * * * */

	/** The principal ID. */
	private final String principal;
	/** All calendars. */
	private final List<CalendarInfo> calendars;

	/* * * * * Constructor * * * * */

	/**
	 * Initializes a new data object.
	 * 
	 * @param principal
	 *            the principal ID
	 * @param calendars
	 *            the calendars
	 * 
	 * @since 1.0
	 */
	public Data(String principal, List<CalendarInfo> calendars) {
		this.principal = principal;
		this.calendars = calendars;
	}

	/* * * * * Methods * * * * */

	/**
	 * Gets the principal ID.
	 * 
	 * @return the principal ID
	 * 
	 * @since 1.0
	 */
	public String getPrincipal() {
		return principal;
	}

	/**
	 * Gets all calendars.
	 * 
	 * @return the calendars
	 * 
	 * @since 1.0
	 */
	public List<CalendarInfo> getCalendars() {
		return calendars;
	}
}

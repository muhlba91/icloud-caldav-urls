package com.niftyside.icloud.calendars.handlers;

/**
 * All needed information about a calendar response.
 * 
 * @author Daniel Muehlbachler
 * @copyright 2012 Daniel Muehlbachler
 * 
 * @see {@link http://icloud.niftyside.com}
 * 
 * @version 1.0
 * 
 */
public class CalendarInfo {
	/* * * * * Variables * * * * */

	/** The calendar name. */
	private String name;
	/** The calendar href. */
	private String href;
	/** The calendar server. */
	private String server;

	/* * * * * Methods * * * * */

	/**
	 * Sets a new name.
	 * 
	 * @param name
	 *            the new name
	 * 
	 * @since 1.0
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets a new href.
	 * 
	 * @param href
	 *            the new href
	 * 
	 * @since 1.0
	 */
	public void setHref(String href) {
		this.href = href;
	}

	/**
	 * Sets a new server.
	 * 
	 * @param server
	 *            the new server
	 * 
	 * @since 1.0
	 */
	public void setServer(String server) {
		this.server = server;
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 * 
	 * @since 1.0
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the href.
	 * 
	 * @return the href
	 * 
	 * @since 1.0
	 */
	public String getHref() {
		return href;
	}

	/**
	 * Gets the calendar URL.
	 * 
	 * @return the URL
	 * 
	 * @since 1.0
	 */
	public String getURL() {
		return "https://" + server + href;
	}
}

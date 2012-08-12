package com.niftyside.icloud.calendars.api.impl;

import java.io.UnsupportedEncodingException;

import com.niftyside.icloud.calendars.api.exception.CalendarException;
import com.niftyside.icloud.calendars.api.model.Calendar;

/**
 * Implementation of {@link Calendar}
 * 
 * @author Daniel Muehlbachler
 * @copyright 2012 Daniel Muehlbachler
 * 
 * @see http://icloud.niftyside.com
 * 
 * @version 1.1
 * 
 */
public class CalendarImpl implements Calendar {
	/* * * * * Variables * * * * */

	/** The calendar name. */
	private final String name;
	/** The calendar href. */
	private final String href;
	/** The calendar server. */
	private final String server;

	/* * * * * Constructor * * * * */

	/**
	 * Creates a new calendar object.
	 * 
	 * @param name
	 *            the name
	 * @param href
	 *            the href
	 * @param server
	 *            the server
	 * @throws CalendarException
	 *             if an error encoding the strings occurs
	 * 
	 * @since 1.1
	 */
	public CalendarImpl(final String name, final String href,
			final String server) throws CalendarException {
		try {
			this.name = new String(name.getBytes(), "UTF-8");
			this.href = new String(href.getBytes(), "UTF-8");
			this.server = new String(server.getBytes(), "UTF-8");
		} catch (final UnsupportedEncodingException e) {
			throw new CalendarException("Error: can't encode to UTF-8.", e);
		}
	}

	/* * * * * Methods * * * * */

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getHref() {
		return href;
	}

	@Override
	public String getURL() {
		return "https://" + server + href;
	}
}

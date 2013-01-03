package com.niftyside.icloud.calendars.api.impl;

import java.io.UnsupportedEncodingException;

import com.niftyside.icloud.calendars.api.exception.CalendarException;
import com.niftyside.icloud.calendars.api.model.Calendar;

/**
 * Implementation of {@link Calendar}
 * 
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 * 
 * @see http://icloud.niftyside.com
 * 
 * @version 1.1
 * 
 */
public class CalendarImpl implements Calendar {
	/* * * * * Variables * * * * */

	private static final String UTF8 = "UTF-8";
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
	public CalendarImpl(final String name, final String href, final String server) throws CalendarException {
		try {
			this.name = new String(name.getBytes(UTF8), UTF8);
			this.href = new String(href.getBytes(UTF8), UTF8);
			this.server = new String(server.getBytes(UTF8), UTF8);
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

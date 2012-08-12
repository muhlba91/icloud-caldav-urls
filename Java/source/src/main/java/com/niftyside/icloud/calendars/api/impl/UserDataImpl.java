package com.niftyside.icloud.calendars.api.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.niftyside.icloud.calendars.api.model.Calendar;
import com.niftyside.icloud.calendars.api.model.UserData;

/**
 * Implementation of {@link UserData}
 * 
 * @author Daniel Muehlbachler
 * @copyright 2012 Daniel Muehlbachler
 * 
 * @see http://icloud.niftyside.com
 * 
 * @version 1.2
 * 
 */
public class UserDataImpl implements UserData {
	/* * * * * Variables * * * * */

	/** The principal ID. */
	private final String principal;
	/** All calendars. */
	private final List<Calendar> calendars;
	/** The maximum lengths. */
	private final Map<String, Integer> maxLengths;

	/* * * * * Constructor * * * * */

	/**
	 * Initializes a new user data object.
	 * 
	 * @param principal
	 *            the principal ID
	 * @param calendars
	 *            the calendars
	 * 
	 * @since 1.1
	 */
	public UserDataImpl(final String principal, final List<Calendar> calendars) {
		this.principal = principal;
		this.calendars = Collections.unmodifiableList(calendars);

		maxLengths = calculateMaximumLengths();
	}

	/* * * * * Methods * * * * */

	@Override
	public String getPrincipal() {
		return principal;
	}

	@Override
	public List<Calendar> getCalendars() {
		return calendars;
	}

	@Override
	public String getPrincipalUrl() {
		return "/" + principal + "/principal/";
	}

	@Override
	public String getCardDavUrl() {
		return "/" + principal + "/carddavhome/card/";
	}

	@Override
	public Map<String, Integer> getMaxLengths() {
		return maxLengths;
	}

	/* * * * * Private methods * * * * */

	/**
	 * Calculates the maximum argument lengths.
	 * 
	 * @return a map containing the maximum lengths
	 */
	private Map<String, Integer> calculateMaximumLengths() {
		final Map<String, Integer> map = new HashMap<>();

		int name = 0, href = 0, url = 0;
		for (final Calendar calendar : calendars) {
			if (calendar.getName().length() > name) {
				name = calendar.getName().length();
			}
			if (calendar.getHref().length() > href) {
				href = calendar.getHref().length();
			}
			if (calendar.getURL().length() > url) {
				url = calendar.getURL().length();
			}
		}

		map.put("name", name);
		map.put("href", href);
		map.put("url", url);

		return Collections.unmodifiableMap(map);
	}
}

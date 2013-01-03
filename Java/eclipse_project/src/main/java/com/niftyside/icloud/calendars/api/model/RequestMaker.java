package com.niftyside.icloud.calendars.api.model;

import com.niftyside.icloud.calendars.api.exception.RequestException;

/**
 * Makes a special XML PROPFIND request and parses it to a string.
 * 
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 * 
 * @see http://icloud.niftyside.com
 * 
 * @version 1.1
 * 
 */
public interface RequestMaker {
	/**
	 * Makes a new request.
	 * 
	 * @param requestType
	 *            the requested type
	 * @return the built request string
	 * @throws RequestException
	 *             if transformation of XML request to String failed
	 * 
	 * @since 1.1
	 */
	String makeRequest(final String requestType) throws RequestException;
}

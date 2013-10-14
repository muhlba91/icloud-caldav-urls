package com.niftyside.icloud.calendars.api.model

import com.niftyside.icloud.calendars.api.exception.RequestException

/**
 * Model for a XML request maker.
 *
 * Project: iCloud calendars
 * User: daniel
 * Date: 23.09.13
 * Time: 16:39
 *
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 *
 * @version 2.0.0
 */
public interface RequestMaker {
	/**
	 * Makes a new PROPFIND request.
	 *
	 * @param requestType
	 *            the requested type
	 * @return the built request string
	 * @throws RequestException
	 *             if transformation of XML request to String failed
	 *
	 * @since 2.0.0
	 */
	def makePropfindRequest(String requestType) throws RequestException
}

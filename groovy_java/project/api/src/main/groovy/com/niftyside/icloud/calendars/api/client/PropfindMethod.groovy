package com.niftyside.icloud.calendars.api.client

import org.apache.hc.client5.http.methods.HttpPost

/**
 * PROPFIND method for {@link org.apache.http.client.HttpClient}.
 *
 * Project: iCloud calendars
 * User: daniel
 * Date: 18.09.13
 * Time: 21:45
 *
 * @author Daniel Muehlbachler
 * @copyright 2011-2016 Daniel Muehlbachler
 *
 * @version 2.1.0
 */
class PropfindMethod extends HttpPost {
	/* * * * * Constructor * * * * */

	/**
	 * Initializes a PROPFIND method.
	 *
	 * @param uri
	 * 		the URI
	 *
	 * @since 2.0.0
	 */
	PropfindMethod(String uri) {
		super(uri)
	}

	/* * * * * Methods * * * * */

	@Override
	String getMethod() {
		"PROPFIND"
	}
}

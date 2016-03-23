package com.niftyside.icloud.calendars.api.client

import com.niftyside.icloud.calendars.api.exception.FactoryException
import org.apache.hc.client5.http.auth.AuthScope
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials
import org.apache.hc.client5.http.impl.sync.BasicCredentialsProvider
import org.apache.hc.client5.http.impl.sync.HttpClients
import org.apache.hc.core5.http.entity.ContentType
import org.apache.hc.core5.http.entity.StringEntity

/**
 * Factory for {@link HttpClient} and {@link PropfindMethod}.
 *
 * Project: iCloud calendars
 * User: daniel
 * Date: 18.09.13
 * Time: 21:52
 *
 * @author Daniel Muehlbachler
 * @copyright 2011-2016 Daniel Muehlbachler
 *
 * @version 2.1.0
 */
class ClientFactory {
	/* * * * * Methods * * * * */

	/**
	 * Creates a new HTTP client.
	 *
	 * @param server
	 * 		the server scope
	 * @param username
	 * 		the username
	 * @param password
	 * 		the password
	 * @param realm
	 * 		the AuthScope realm
	 * @return
	 * the configured HttpClient
	 *
	 * @since 2.1.0
	 */
	def getHttpClient(String server, String username, String password, String realm) {
		// setup authentication
		def credentialsProvider = new BasicCredentialsProvider()
		credentialsProvider.setCredentials(new AuthScope(server, 443, realm), getCredentials(username, password))

		HttpClients.custom().setDefaultCredentialsProvider(credentialsProvider).build()
	}

	/**
	 * Creates a new PROPFIND method.
	 *
	 * @param server
	 * 		the server
	 * @param request
	 * 		the request to send
	 * @return
	 * the configured method
	 *
	 * @since 2.0.0
	 */
	def getPropfindMethod(String server, String request) {
		def propfind = new PropfindMethod(server)

		// set needed data including request
		try {
			propfind.setEntity(new StringEntity(request, ContentType.create("text/xml", "UTF-8")))
		} catch(final UnsupportedEncodingException e) {
			throw new FactoryException("Error: couldn't initialize encoding.", e)
		}
		propfind.setHeader("Content-type", "text/xml; charset=UTF-8")
		propfind.setHeader("Depth", "1")

		propfind
	}

	/* * * * * Private methods * * * * */

	/**
	 * Creates a new credentials object.
	 *
	 * @param server
	 * 		the server
	 * @param username
	 * 		the username
	 * @param password
	 * 		the password
	 * @return
	 * the configured credentials
	 *
	 * @since 2.0.0
	 */
	private getCredentials(String username, String password) {
		new UsernamePasswordCredentials(username, password.chars)
	}
}

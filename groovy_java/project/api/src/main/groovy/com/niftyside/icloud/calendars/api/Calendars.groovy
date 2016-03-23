package com.niftyside.icloud.calendars.api

import com.niftyside.icloud.calendars.api.client.ClientFactory
import com.niftyside.icloud.calendars.api.exception.*
import com.niftyside.icloud.calendars.api.impl.BasicRequestMaker
import com.niftyside.icloud.calendars.api.impl.BasicXMLReader
import com.niftyside.icloud.calendars.api.impl.DefaultUserData
import org.apache.hc.client5.http.impl.BasicResponseHandler
import org.apache.hc.client5.http.sync.HttpClient
import org.apache.hc.core5.http.entity.EntityUtils

/**
 * The iCloud calendars API for querying calendar information.
 *
 * Project: iCloud calendars
 * User: daniel
 * Date: 18.09.13
 * Time: 21:35
 *
 * @author Daniel Muehlbachler
 * @copyright 2011-2016 Daniel Muehlbachler
 *
 * @see {@link http://icloud.niftyside.com}
 *
 * @version 2.1.0
 */
class Calendars {
	/* * * * * Variables * * * * */

	public static final VERSION = "2.1.0"
	public static final COPYRIGHT_YEARS = "2011-2016"
	public static final COPYRIGHT_NAME = "NiftySide - Daniel Muehlbachler (http://www.niftyside.com)"
	public static final SERVERS = (1..24).collect {
		"p" + String.format("%02d", it) + "-caldav.icloud.com"
	} as String[]
	private static final CAL_DAV_REALM = "MMCalDav"
	private static final CARD_DAV_REALM = "Newcastle"
	private final clientFactory
	private final reader
	private final requestMaker
	private server
	private username
	private password

	/* * * * * Constructor * * * * */

	/**
	 * Creates a new Calendars API query engine.
	 *
	 * @throws CalendarsException
	 * 		if initialization of the engine failed
	 *
	 * @since 2.0.0
	 */
	Calendars() throws CalendarsException {
		this(null, null, null)
	}

	/**
	 * Creates a new Calendars API query engine.
	 *
	 * @param server
	 * 		the server to use
	 * @param username
	 * 		the username
	 * @param password
	 * 		the password
	 * @throws CalendarsException
	 * 		if initialization of the engine failed
	 *
	 * @since 2.0.0
	 */
	Calendars(String server, String username, String password) throws CalendarsException {
		disableHttpClientLogging()

		this.server = server
		this.username = username
		this.password = password

		try {
			clientFactory = new ClientFactory()
			reader = new BasicXMLReader()
			requestMaker = new BasicRequestMaker()
		} catch(Exception e) {
			throw new CalendarsException("Couldn't initialize query engine!", e)
		}
	}

	/* * * * * Methods * * * * */

	/**
	 * Sets a new server to connect to.
	 *
	 * @param server
	 * 			the server
	 *
	 * @since 2.0.0
	 */
	def setServer(String server) {
		this.server = server
	}

	/**
	 * Sets new login data.
	 *
	 * @param server
	 * 			the server
	 * @param username
	 * 			the username
	 * @param password
	 * 			the password
	 *
	 * @since 2.0.0
	 */
	def setLoginData(String server, String username, String password) {
		this.server = server
		this.username = username
		this.password = password
	}

	/**
	 * Sets new login data.
	 *
	 * @param username
	 * 			the username
	 * @param password
	 * 			the password
	 *
	 * @since 2.0.0
	 */
	def setLoginData(String username, String password) {
		this.username = username
		this.password = password
	}

	/**
	 * Gets the queried {@link UserData}.
	 *
	 * @param server
	 *            the server
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @return the fetched user data
	 * @throws CalendarsException
	 *             if any error occurs
	 *
	 * @since 2.0.0
	 */
	def getUserData() throws CalendarsException {
		try {
			createUserData()
		} catch(XMLException | RequestException | FactoryException | ServerException e) {
			throw new CalendarsException("Couldn't fetch requested data!", e)
		}
	}

	/* * * * * Private methods * * * * */

	/**
	 * Disables {@link HttpClient} logging.
	 *
	 * @since 2.0.0
	 */
	private static void disableHttpClientLogging() {
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog")
		System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true")
		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient", "fatal")
	}

	/**
	 * Creates the user data object by fetching all details.
	 *
	 * @return the user data
	 * @throws RequestException
	 *             if the request failed
	 * @throws FactoryException
	 *             if the {@link PropfindMethod} couldn't get initialized or the
	 * {@link HttpClient} couldn't get created
	 * @throws ServerException
	 *             if an error during communicating with or authenticating on
	 *             the server occurs
	 * @throws XMLException
	 *             if the XML parsing fails
	 *
	 * @since 2.0.0
	 */
	private createUserData() throws RequestException, FactoryException, ServerException, XMLException {
		def caldavClient = clientFactory.getHttpClient(server, username, password, CAL_DAV_REALM)
		def carddavClient = clientFactory.getHttpClient(cardDavServer, username, password, CARD_DAV_REALM)

		def principal = getPrincipal(caldavClient)
		def calendars = getCalendars(caldavClient, principal)
		def cardDavUrl = getCardDavUrl(carddavClient, principal)

		new DefaultUserData(server, principal, calendars, cardDavUrl == "N/A" ? null : cardDavUrl)
	}

	/**
	 * Gets the principal.
	 *
	 * @param client
	 *            the http client
	 * @return the principal
	 * @throws RequestException
	 *             if the request couldn't get created
	 * @throws FactoryException
	 *             if the {@link PropfindMethod} couldn't get initialized
	 * @throws ServerException
	 *             if an error communicating with or authenticating on the
	 *             server occurs
	 * @throws XMLException
	 *             if the response couldn't get parsed
	 *
	 * @since 2.0.0
	 */
	private getPrincipal(HttpClient client) throws RequestException, FactoryException, ServerException, XMLException {
		def request = requestMaker.makePropfindRequest("current-user-principal")
		def propfind = clientFactory.getPropfindMethod("https://" + server + ":443/", request)

		performPropfindRequest(client, propfind)
		reader.principal
	}

	/**
	 * Gets the CardDAV URLs.
	 *
	 * @param client
	 *            the http client
	 * @param principal
	 *            the principal
	 * @return the CardDAV URL
	 * @throws RequestException
	 *             if the request couldn't get created
	 * @throws FactoryException
	 *             if the {@link PropfindMethod} couldn't get initialized
	 * @throws ServerException
	 *             if an error communicating with or authenticating on the
	 *             server occurs
	 * @throws XMLException
	 *             if the response couldn't get parsed
	 *
	 * @since 2.1.0
	 */
	private getCardDavUrl(HttpClient client, String principal) throws RequestException, FactoryException, ServerException, XMLException {
		def request = requestMaker.makePropfindRequest("addressbook-home-set", ['xmlns:A': 'urn:ietf:params:xml:ns:carddav'])
		def propfind = clientFactory.getPropfindMethod("https://" + cardDavServer + ":443/" + principal + "/principal/", request)

		performPropfindRequest(client, propfind)
		reader.cardDavUrl
	}

	/**
	 * Gets the calendars.
	 *
	 * @param client
	 *            the http client
	 * @param principal
	 *            the principal
	 * @return the calendars
	 * @throws RequestException
	 *             if the request couldn't get created
	 * @throws FactoryException
	 *             if the {@link PropfindMethod} couldn't get initialized
	 * @throws ServerException
	 *             if an error communicating with or authenticating on the
	 *             server occurs
	 * @throws XMLException
	 *             if the response couldn't get parsed
	 *
	 * @since 2.0.0
	 */
	private getCalendars(HttpClient client, String principal) throws RequestException, FactoryException, ServerException, XMLException {
		def request = requestMaker.makePropfindRequest("displayname")
		def propfind = clientFactory.getPropfindMethod("https://" + server + ":443/" + principal + "/calendars/", request)

		performPropfindRequest(client, propfind)
		reader.getCalendars(server)
	}

	/**
	 * Performs a propfind request and sets the result in the reader.
	 *
	 * @param client
	 * 		the http client
	 * @param propfind
	 * 		the request to execute
	 *
	 * @since 2.0.2
	 */
	private void performPropfindRequest(HttpClient client, def propfind) {
		def responseBody = null
		def response = null
		try {
			response = client.execute(propfind)

			if(response.statusLine.statusCode != 207) {
				throw new ServerException("Unable to authenticate.", null)
			}

			responseBody = new BasicResponseHandler().handleResponse(response)
		} catch(final IOException e) {
			throw new ServerException("Can't make request.", e)
		} finally {
			if(response != null) {
				EntityUtils.consume(response.entity)
			}
		}

		reader.setXML(responseBody)
	}

	/**
	 * Gets the CardDAV server URL.
	 *
	 * @return the CardDAV server URL
	 *
	 * @since 2.1.0
	 */
	private String getCardDavServer() {
		server.replace("caldav", "contacts")
	}
}

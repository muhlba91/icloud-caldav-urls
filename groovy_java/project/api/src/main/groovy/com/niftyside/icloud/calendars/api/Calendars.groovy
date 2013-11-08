package com.niftyside.icloud.calendars.api

import com.niftyside.icloud.calendars.api.client.ClientFactory
import com.niftyside.icloud.calendars.api.exception.*
import com.niftyside.icloud.calendars.api.impl.BasicRequestMaker
import com.niftyside.icloud.calendars.api.impl.BasicXMLReader
import com.niftyside.icloud.calendars.api.impl.DefaultUserData
import org.apache.http.client.HttpClient
import org.apache.http.impl.client.BasicResponseHandler
import org.apache.http.util.EntityUtils

/**
 * The iCloud calendars API for querying calendar information.
 *
 * Project: iCloud calendars
 * User: daniel
 * Date: 18.09.13
 * Time: 21:35
 *
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 *
 * @see {@link http://icloud.niftyside.com}
 *
 * @version 2.0.1
 */
class Calendars {
	/* * * * * Variables * * * * */

	public static final def VERSION = "2.0.1"
	public static final def COPYRIGHT_YEARS = "2011-2013"
	public static final def COPYRIGHT_NAME = "NiftySide - Daniel Muehlbachler (http://www.niftyside.com)"
	public static final def SERVERS = (1..24).collect { "p" + String.format("%02d", it) + "-caldav.icloud.com" } as String[]
	private final def clientFactory
	private final def reader
	private final def requestMaker
	private def server
	private def username
	private def password

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
			throw new CalendarsException("Couldn't fetch requested data!", e);
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
	private def createUserData() throws RequestException, FactoryException, ServerException, XMLException {
		def client = clientFactory.getHttpClient(server, username, password)

		def principal = getPrincipal(client)
		def calendars = getCalendars(client, principal)

		new DefaultUserData(server, principal, calendars)
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
	private def getPrincipal(HttpClient client) throws RequestException, FactoryException, ServerException, XMLException {
		def request = requestMaker.makePropfindRequest("current-user-principal")
		def propfind = clientFactory.getPropfindMethod("https://" + server + ":443/", request)

		def responseBody = null
		def response = null
		try {
			response = client.execute(propfind)

			if(response.getStatusLine().statusCode != 207) {
				throw new ServerException("Unable to authenticate.", null)
			}

			responseBody = new BasicResponseHandler().handleResponse(response)
		} catch(final IOException e) {
			throw new ServerException("Can't make request.", e)
		} finally {
			if(response != null) {
				EntityUtils.consume(response.getEntity())
			}
		}

		reader.setXML(responseBody)
		reader.getPrincipal()
	}

	/**
	 * Gets the principal.
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
	private def getCalendars(HttpClient client, String principal) throws RequestException, FactoryException, ServerException, XMLException {
		def request = requestMaker.makePropfindRequest("displayname")
		def propfind = clientFactory.getPropfindMethod("https://" + server + ":443/" + principal + "/calendars/", request)

		def responseBody = null
		def response = null
		try {
			response = client.execute(propfind)

			if(response.getStatusLine().statusCode != 207) {
				throw new ServerException("Unable to authenticate.", null)
			}

			responseBody = new BasicResponseHandler().handleResponse(response)
		} catch(final IOException e) {
			throw new ServerException("Can't make request.", e)
		} finally {
			if(response != null) {
				EntityUtils.consume(response.getEntity())
			}
		}

		reader.setXML(responseBody)
		reader.getCalendars(server)
	}
}

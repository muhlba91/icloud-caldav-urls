package com.niftyside.icloud.calendars.api;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;

import com.niftyside.icloud.calendars.api.client.ClientFactory;
import com.niftyside.icloud.calendars.api.client.PropFindMethod;
import com.niftyside.icloud.calendars.api.exception.FactoryException;
import com.niftyside.icloud.calendars.api.exception.ICloudException;
import com.niftyside.icloud.calendars.api.exception.RequestException;
import com.niftyside.icloud.calendars.api.exception.ServerException;
import com.niftyside.icloud.calendars.api.exception.XMLException;
import com.niftyside.icloud.calendars.api.impl.RequestMakerImpl;
import com.niftyside.icloud.calendars.api.impl.UserDataImpl;
import com.niftyside.icloud.calendars.api.impl.XMLReaderImpl;
import com.niftyside.icloud.calendars.api.model.Calendar;
import com.niftyside.icloud.calendars.api.model.ExceptionModel;
import com.niftyside.icloud.calendars.api.model.RequestMaker;
import com.niftyside.icloud.calendars.api.model.UserData;
import com.niftyside.icloud.calendars.api.model.XMLReader;

/**
 * The iCloud API for querying the calendar information.
 * 
 * @author Daniel Muehlbachler
 * @copyright 2012 Daniel Muehlbachler
 * 
 * @see http://icloud.niftyside.com
 * 
 * @version 1.1
 * 
 */
public class ICloud {
	/* * * * * Variables * * * * */

	/** The API version. */
	public static final String VERSION = "1.1";
	/** The XML reader to use. */
	private final XMLReader reader;
	/** The client factory. */
	private final ClientFactory factory;
	/** The request maker. */
	private final RequestMaker requestMaker;
	/** The server to use. */
	private final String server;
	/** The username to use. */
	private final String username;
	/** The password to use. */
	private final String password;

	/* * * * * Static methods * * * * */

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
	 * @throws ICloudException
	 *             if any error occurs; see {@link ExceptionModel} for special
	 *             details
	 * 
	 * @since 1.1
	 */
	public static UserData queryData(final String server,
			final String username, final String password)
			throws ICloudException {
		disableHttpClientLogging();

		UserData userData = null;

		try {
			final ICloud icloud = new ICloud(server, username, password);

			userData = icloud.getUserData();
		} catch (XMLException | RequestException | FactoryException
				| ServerException e) {
			throw new ICloudException("Error: coulnd't fetch requested data.",
					e);
		}

		return userData;
	}

	/* * * * * Constructor * * * * */

	/**
	 * Creates a new iCloud querying object.
	 * 
	 * @param server
	 *            the server
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @throws RequestException
	 *             if the request maker hadn't been initialized
	 * 
	 * @since 1.1
	 */
	private ICloud(final String server, final String username,
			final String password) throws RequestException {
		this.server = server;
		this.username = username;
		this.password = password;

		factory = new ClientFactory();
		reader = new XMLReaderImpl();
		requestMaker = new RequestMakerImpl();
	}

	/* * * * * Private methods * * * * */

	/**
	 * Gets the {@link UserData}.
	 * 
	 * @return the user data
	 * @throws RequestException
	 *             if the request failed
	 * @throws FactoryException
	 *             if the {@link PropFindMethod} couldn't get initialized or the
	 *             {@link HttpClient} couldn't get created
	 * @throws ServerException
	 *             if an error during communicating with or authenticating on
	 *             the server occurs
	 * @throws XMLException
	 *             if the XML parsing fails
	 * 
	 * @since 1.1
	 */
	private UserData getUserData() throws RequestException, FactoryException,
			ServerException, XMLException {
		final HttpClient client = factory.getHttpClient(server, username,
				password);

		final String principal = getPrincipal(client);

		final List<Calendar> calendars = getCalendars(client, principal);

		return new UserDataImpl(principal, calendars);
	}

	/**
	 * Gets the principal.
	 * 
	 * @param client
	 *            the {@link HttpClient}
	 * @param principal
	 *            the principal
	 * @return the {@link Calendar}s
	 * @throws RequestException
	 *             if the request couldn't get created
	 * @throws FactoryException
	 *             if the {@link PropFindMethod} couldn't get initialized
	 * @throws ServerException
	 *             if an error communicating with or authenticating on the
	 *             server occurs
	 * @throws XMLException
	 *             if the response couldn't get parsed
	 * 
	 * @since 1.1
	 */
	private List<Calendar> getCalendars(final HttpClient client,
			final String principal) throws RequestException, FactoryException,
			ServerException, XMLException {
		final String request = requestMaker.makeRequest("displayname");
		final PropFindMethod propfind = factory.getPropfind("https://" + server
				+ ":443/" + principal + "/calendars/", request);

		String response = null;
		try {
			client.executeMethod(propfind);

			if (propfind.getStatusCode() != 207)
				throw new ServerException("Error: unable to authenticate.",
						null);

			response = URLDecoder.decode(propfind.getResponseBodyAsString(),
					propfind.getResponseCharSet());
		} catch (final IOException e) {
			throw new ServerException("Error: can't make request.", e);
		} finally {
			propfind.releaseConnection();
		}

		reader.setXML(response);
		return reader.getCalendars(server);
	}

	/* * * * * Private methods * * * * */

	/**
	 * Disables {@link HttpClient} logging.
	 * 
	 * @since 1.1
	 */
	private static void disableHttpClientLogging() {
		System.setProperty("org.apache.commons.logging.Log",
				"org.apache.commons.logging.impl.SimpleLog");
		System.setProperty("org.apache.commons.logging.simplelog.showdatetime",
				"true");
		System.setProperty(
				"org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient",
				"fatal");
	}

	/**
	 * Gets the principal.
	 * 
	 * @param client
	 *            the {@link HttpClient}
	 * @return the principal
	 * @throws RequestException
	 *             if the request couldn't get created
	 * @throws FactoryException
	 *             if the {@link PropFindMethod} couldn't get initialized
	 * @throws ServerException
	 *             if an error communicating with or authenticating on the
	 *             server occurs
	 * @throws XMLException
	 *             if the response couldn't get parsed
	 * 
	 * @since 1.1
	 */
	private String getPrincipal(final HttpClient client)
			throws RequestException, FactoryException, ServerException,
			XMLException {
		final String request = requestMaker
				.makeRequest("current-user-principal");
		final PropFindMethod propfind = factory.getPropfind("https://" + server
				+ ":443/", request);

		String response = null;

		try {
			client.executeMethod(propfind);

			if (propfind.getStatusCode() != 207)
				throw new ServerException("Error: unable to authenticate.",
						null);

			response = URLDecoder.decode(propfind.getResponseBodyAsString(),
					propfind.getResponseCharSet());
		} catch (final IOException e) {
			throw new ServerException("Error: can't make request", e);
		} finally {
			propfind.releaseConnection();
		}

		reader.setXML(response);
		return reader.getPrincipal();
	}
}

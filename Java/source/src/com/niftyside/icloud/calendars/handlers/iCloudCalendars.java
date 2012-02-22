package com.niftyside.icloud.calendars.handlers;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;

import com.niftyside.icloud.calendars.exceptions.FactoryException;
import com.niftyside.icloud.calendars.exceptions.RequestException;
import com.niftyside.icloud.calendars.exceptions.ServerException;
import com.niftyside.icloud.calendars.exceptions.XMLException;

/**
 * Main class for handling all requests and responses regarding iCloud
 * calendars.
 * 
 * @author Daniel Muehlbachler
 * @copyright 2012 Daniel Muehlbachler
 * 
 * @see {@link http://icloud.niftyside.com}
 * 
 * @version 1.0
 * 
 */
public class iCloudCalendars {
	/* * * * * Variables * * * * */

	/** The XML reader to use - initialize before using it!!! */
	private final XMLReader reader;
	/** The request maker. */
	private final RequestMaker requestMaker;
	/** The client factory. */
	private final ClientFactory factory;
	/** The server to use. */
	private String server = "p01-caldav.icloud.com";
	/** The username to use. */
	private String username;
	/** The password to use. */
	private String password;

	/* * * * * Constructor * * * * */

	/**
	 * Initialize a new instance.
	 * 
	 * @throws RequestException
	 *             if the request maker couldn't get initialized
	 * 
	 * @since 1.0
	 */
	public iCloudCalendars() throws RequestException {
		reader = new XMLReader();
		requestMaker = new RequestMaker();
		factory = new ClientFactory();

		clearCredentials();
	}

	/* * * * * Methods * * * * */

	/**
	 * Sets all needed credentials.
	 * 
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * 
	 * @since 1.0
	 */
	public void setCredentials(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * Deletes all credentials.
	 * 
	 * @since 1.0
	 */
	public void clearCredentials() {
		username = "";
		password = "";
	}

	/**
	 * Sets a new server.
	 * 
	 * @param server
	 *            the server
	 * 
	 * @since 1.0
	 */
	public void setServer(String server) {
		this.server = server;
	}

	/**
	 * Fetches all data.
	 * 
	 * @return all data
	 * @throws RequestException
	 *             if the request couldn't get build
	 * @throws FactoryException
	 *             if the factory couldn't create the clients
	 * @throws XMLException
	 *             if the XML reader throws an error
	 * @throws ServerException
	 *             if any error during communicating with the servers occurs
	 * 
	 * @since 1.0
	 */
	public Data getData() throws RequestException, FactoryException,
			XMLException, ServerException {
		return makeRequests();
	}

	/* * * * * Private methods * * * * */

	/**
	 * Performs all needed requests to fetch all data.
	 * 
	 * @return all data
	 * @throws RequestException
	 *             if the request couldn't get build
	 * @throws FactoryException
	 *             if the factory couldn't create the clients
	 * @throws XMLException
	 *             if the XML reader throws an error
	 * @throws ServerException
	 *             if any error during communicating with the servers occurs
	 * 
	 * @since 1.0
	 */
	private Data makeRequests() throws RequestException, FactoryException,
			XMLException, ServerException {
		PropFindMethod propfind;
		String request, response;
		HttpClient client = factory.getHttpClient(server, username, password);
		List<CalendarInfo> calendars = new ArrayList<CalendarInfo>();
		String principal;

		// get principal
		request = requestMaker.make("current-user-principal");
		propfind = factory.getPropfind("https://" + server + ":443/", request);
		// make request
		try {
			client.executeMethod(propfind);
			// check for error
			if (propfind.getStatusCode() != 207)
				throw new ServerException("Error: unable to authenticate.");
			response = URLDecoder.decode(propfind.getResponseBodyAsString(),
					propfind.getResponseCharSet());
		} catch (IOException e) {
			throw new ServerException("Error: can't make request.");
		} finally {
			propfind.releaseConnection();
		}
		reader.setXML(response);
		principal = reader.getPrincipal();

		// get calendars
		request = requestMaker.make("displayname");
		propfind = factory.getPropfind("https://" + server + ":443/"
				+ principal + "/calendars/", request);
		// make request
		try {
			client.executeMethod(propfind);
			if (propfind.getStatusCode() != 207)
				throw new ServerException("Error: unable to authenticate.");
			response = URLDecoder.decode(propfind.getResponseBodyAsString(),
					propfind.getResponseCharSet());
		} catch (IOException e) {
			throw new ServerException("Error: can't make request.");
		} finally {
			propfind.releaseConnection();
		}
		reader.setXML(response);
		calendars = reader.getResponses(server);

		// return
		return new Data(principal, calendars);
	}
}

package com.niftyside.icloud.calendars.handlers;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import com.niftyside.icloud.calendars.exceptions.FactoryException;

/**
 * Factory that produces {@link HttpClient}s and {@link PropFindMethod}s.
 * 
 * @author Daniel Muehlbachler
 * @copyright 2012 Daniel Muehlbachler
 * 
 * @see {@link http://icloud.niftyside.com}
 * 
 * @version 1.0
 * 
 */
public class ClientFactory {
	/**
	 * Creates a new {@link HttpClient}.
	 * 
	 * @param server
	 *            the server scope
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @return the configured HttpClient
	 * 
	 * @since 1.0
	 */
	public HttpClient getHttpClient(String server, String username,
			String password) {
		HttpClient client = new HttpClient();

		// set authentication
		client.getState().setCredentials(
				new AuthScope(server, 443, "MMCalDav"),
				new UsernamePasswordCredentials(username, password));

		return client;
	}

	/**
	 * Creates a new {@link PropFindMethod}.
	 * 
	 * @param server
	 *            the server
	 * @param request
	 *            the request to send
	 * @return the configured PropFindMethod
	 * @throws FactoryException
	 *             if the PROPFIND method couldn't be initialized correctly
	 * 
	 * @since 1.0
	 */
	public PropFindMethod getPropfind(String server, String request)
			throws FactoryException {
		PropFindMethod propfind = new PropFindMethod(server);

		// set needed data including request
		try {
			propfind.setRequestEntity(new StringRequestEntity(request,
					"text/xml", "UTF-8"));
		} catch (Exception e) {
			throw new FactoryException("Error: couldn't initialize encoding.");
		}
		propfind.setRequestHeader("Content-type", "text/xml; charset=UTF-8");
		propfind.setRequestHeader("Depth", "1");
		propfind.setDoAuthentication(true);

		return propfind;
	}
}

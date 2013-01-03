package com.niftyside.icloud.calendars.app;

import java.util.HashMap;
import java.util.Map;

import com.niftyside.icloud.calendars.api.ICloud;
import com.niftyside.icloud.calendars.api.exception.ICloudException;
import com.niftyside.icloud.calendars.api.model.Calendar;
import com.niftyside.icloud.calendars.api.model.UserData;

/**
 * The query engine application for command line access.<br>
 * INFO: Queries the first iCloud server!
 * 
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 * 
 * @see http://icloud.niftyside.com
 * 
 * @version 1.2.1
 * 
 */
public final class CmdLine {
	/* * * * * Variables * * * * */

	/** The server. */
	private static final String SERVER = "p01-caldav.icloud.com";

	/* * * * * Methods * * * * */

	/**
	 * Main application method.
	 * 
	 * @param args
	 *            command line arguments
	 * 
	 * @since 1.1
	 */
	public static void main(final String[] args) {
		printHead();

		if (args.length != 2) {
			printError("Error: invalid argument count.");
		} else {
			final Map<String, String> arguments = parseArguments(args);
			try {
				printCalendarInformation(ICloud.queryData(SERVER, arguments.get("username"), arguments.get("password")));
			} catch (final ICloudException e) {
				printError(e.getMessage());
			}
		}
	}

	/* * * * * Avoid instantiation * * * * */

	/**
	 * Avoids instantiation.
	 * 
	 * @since 1.2.1
	 */
	private CmdLine() {
		// nothing to do
	}

	/* * * * * Private methods * * * * */

	/**
	 * Prints the header.
	 * 
	 * @since 1.2.1
	 */
	private static void printHead() {
		System.out
				.println("iCloud calendar URL fetcher\n"
						+ "Version: "
						+ AppConstants.CMD_VERSION
						+ "\n"
						+ "API version: "
						+ ICloud.VERSION
						+ "\n\n"
						+ "This application will help you with getting your iCloud calendar URLs"
						+ "to use them in third-party clients like Thunderbird.\n"
						+ "It is explicitly PROHIBITED to:\n"
						+ "\t* publish this app or the source code\n"
						+ "\t* modify this app or the source code\n"
						+ "For more information about this app and the the license conditions visit http://icloud.niftyside.com.\n"
						+ "As this application is freeware, please consider making a small donation! Thank you!\n\n"
						+ "Copyright (C) " + AppConstants.COPYRIGHT_YEARS + "by " + AppConstants.COPYRIGHT_NAME
						+ "\n\n");
	}

	/**
	 * Prints the error message followed by the help message.
	 * 
	 * @param msg
	 *            the message
	 * 
	 * @since 1.1
	 */
	private static void printError(final String msg) {
		System.out.println(msg + "\n");
		printHelp();
	}

	/**
	 * Prints the help message.
	 * 
	 * @since 1.1
	 */
	private static void printHelp() {
		System.out.println("HELP:\n" + "Paramters: <username> <password>\n" + "\t<username>: your Apple ID\n"
				+ "\t<password>: your password\n");
	}

	/**
	 * Parses the command line arguments.
	 * 
	 * @param args
	 *            the arguments
	 * @return a map containing the arguments
	 * 
	 * @since 1.1
	 */
	private static Map<String, String> parseArguments(final String[] args) {
		final Map<String, String> map = new HashMap<>();

		map.put("username", args[0]);
		map.put("password", args[1]);

		return map;
	}

	/**
	 * Prints the fetched calendar information.
	 * 
	 * @param queryData
	 *            the queried data {@link UserData}
	 * 
	 * @since 1.2
	 */
	private static void printCalendarInformation(final UserData queryData) {
		if (queryData == null) {
			printError("Error: unknown.");
			return;
		}

		System.out.println("Your principal: " + queryData.getPrincipal());
		System.out.println("Principal-URL: " + queryData.getPrincipalUrl());
		System.out.println("Contacts-URL: " + queryData.getCardDavUrl() + "\n" + "Your calendars:");

		final Map<String, Integer> lengths = queryData.getMaxLengths();
		final String format = "%-" + (lengths.get("name") + 3) + "s %-" + (lengths.get("href") + 3) + "s %-"
				+ (lengths.get("url") + 3) + "s\n";

		System.out.printf(format, "NAME", "HREF", "URL");
		for (final Calendar calendar : queryData.getCalendars()) {
			System.out.printf(format, calendar.getName(), calendar.getHref(), calendar.getURL());
		}

		System.out.println();
	}
}

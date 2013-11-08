package com.niftyside.icloud.calendars.app.cmdline

import com.niftyside.icloud.calendars.api.Calendars
import com.niftyside.icloud.calendars.api.exception.CalendarsException

import java.util.concurrent.ConcurrentHashMap

/**
 * The query engine application for command line access.<br>
 * INFO: Queries the first iCloud server!
 *
 * Project: iCloud calendars
 * User: daniel
 * Date: 29.09.13
 * Time: 13:45
 *
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 *
 * @see {@link http://icloud.niftyside.com}
 *
 * @version 2.0.0
 */
class Application {
	/* * * * * Variables * * * * */

	public static final def VERSION = "2.0.0"
	private static final def DEFAULT_SERVER = Calendars.SERVERS[0]
	private def server
	private def engine

	/* * * * * Methods * * * * */

	/**
	 * Main application method.
	 *
	 * @param args
	 *            command line arguments
	 *
	 * @since 2.0.0
	 */
	public static void main(String[] args) {
		if(args.length == 2) {
			def arguments = parseArguments(args)

			try {
				def application = new Application(DEFAULT_SERVER, arguments.get("username"), arguments.get("password"))
				application.print()
			} catch(CalendarsException e) {
				printError(e.getMessage())
			}
		} else {
			printError("Error: invalid argument count!")
		}
	}

	/* * * * * Constructor * * * * */

	/**
	 * Creates a new application object.
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
	private Application(String server, String username, String password) {
		this.server = server
		engine = new Calendars(server, username, password)
	}

	/* * * * * Methods * * * * */

	/**
	 * Fetched and prints the user data.
	 *
	 * @since 2.0.0
	 */
	def print() {
		printHead()

		def queryData = engine.getUserData()
		if(queryData == null) {
			printError("Error: unknown.")
			return
		}

		println "Principal ID: " + queryData.getPrincipal()
		println "Principal-URL: " + queryData.getPrincipalUrl()
		println "Contacts-URL: " + queryData.getCardDavUrl() + "\n" + "Your calendars:"

		def lengths = queryData.getMaxLengths();
		def format = "%-" + (lengths.get("name") + 3) + "s %-" + (lengths.get("href") + 3) + "s %-" + (lengths.get("url") + 3) + "s\n"

		printf format, "NAME", "HREF", "URL"
		queryData.getCalendars().each { calendar ->
			printf format, calendar.getName(), calendar.getHref(), calendar.getURL()
		}

		println()
	}

	/**
	 * Prints the header.
	 *
	 * @since 2.0.0
	 */
	private static def printHead() {
		println "iCloud command line calendar URL fetcher\n" +
				"Version: " + VERSION + "\n" +
				"API version: " + Calendars.VERSION + "\n\n" +
				"This application will help you with getting your iCloud calendar URLs" +
				"to use them in third-party clients like Thunderbird.\n" +
				"It is explicitly PROHIBITED to:\n" +
				"\t* publish this app or the source code\n" +
				"\t* modify this app or the source code\n" +
				"For more information about this app and the the license conditions visit http://icloud.niftyside.com.\n" +
				"As this application is freeware, please consider making a small donation! Thank you!\n\n" +
				"Copyright (C) " + Calendars.COPYRIGHT_YEARS + " by " + Calendars.COPYRIGHT_NAME + "\n\n"
	}

	/* * * * * Private methods * * * * */

	/**
	 * Prints the error message followed by the help message.
	 *
	 * @param msg
	 *            the message
	 *
	 * @since 2.0.0
	 */
	private static def printError(String msg) {
		println msg + "\n"
		printHelp()
	}

	/**
	 * Prints the help message.
	 *
	 * @since 2.0.0
	 */
	private static def printHelp() {
		println "HELP:\n" + "Paramters: <username> <password>\n" + "\t<username>: your Apple ID\n" + "\t<password>: your password\n"
	}

	/**
	 * Parses the command line arguments.
	 *
	 * @param args
	 *            the arguments
	 * @return a map containing the arguments
	 *
	 * @since 2.0.0
	 */
	private static def parseArguments(String[] args) {
		def map = new ConcurrentHashMap<String, String>()

		map.put("username", args[0])
		map.put("password", args[1])

		map
	}
}

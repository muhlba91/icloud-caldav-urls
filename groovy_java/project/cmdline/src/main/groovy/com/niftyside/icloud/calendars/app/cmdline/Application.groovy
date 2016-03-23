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
 * @copyright 2011-2016 Daniel Muehlbachler
 *
 * @see {@link http://icloud.niftyside.com}
 *
 * @version 2.1.0
 */
class Application {
	/* * * * * Variables * * * * */

	public static final VERSION = "2.1.0"
	private static final DEFAULT_SERVER = Calendars.SERVERS[0]
	private server
	private engine

	/* * * * * Methods * * * * */

	/**
	 * Main application method.
	 *
	 * @param args
	 *            command line arguments
	 *
	 * @since 2.0.0
	 */
	static void main(String[] args) {
		if(args.length == 2) {
			def arguments = parseArguments(args)

			try {
				def application = new Application(DEFAULT_SERVER, arguments.get("username"), arguments.get("password"))
				application.print()
			} catch(CalendarsException e) {
				printError(e.message)
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

		def queryData = engine.userData
		if(queryData == null) {
			printError("Error: unknown.")
			return
		}

		println "Principal ID: " + queryData.principal
		println "Principal-URL: " + queryData.principalUrl
		println "Contacts-URL: " + queryData.cardDavUrl + "\n" + "Your calendars:"

		def lengths = queryData.maxLengths
		def format = "%-" + (lengths.get("name") + 3) + "s %-" + (lengths.get("href") + 3) + "s %-" + (lengths.get("url") + 3) + "s\n"

		printf format, "NAME", "HREF", "URL"
		queryData.calendars.each { calendar ->
			printf format, calendar.name, calendar.href, calendar.getURL()
		}

		println()
	}

	/**
	 * Prints the header.
	 *
	 * @since 2.0.0
	 */
	private static printHead() {
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
	private static printError(String msg) {
		println msg + "\n"
		printHelp()
	}

	/**
	 * Prints the help message.
	 *
	 * @since 2.0.0
	 */
	private static printHelp() {
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
	private static parseArguments(String[] args) {
		def map = new ConcurrentHashMap<String, String>()

		map.put("username", args[0])
		map.put("password", args[1])

		map
	}
}

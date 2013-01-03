package com.niftyside.icloud.calendars.app;

/**
 * Application related constants.
 * 
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 * 
 * @see http://icloud.niftyside.com
 * 
 * @version 1.2.1
 * 
 */
public final class AppConstants {
	/* * * * * General constants * * * * */

	/** The years of copyright. */
	public static final String COPYRIGHT_YEARS = "2011-2013";
	/** The name of the copyright holder. */
	public static final String COPYRIGHT_NAME = "NiftySide - Daniel Muehlbachler (http://www.niftyside.com)";

	/* * * * * Command line constants * * * * */

	/** The command line version. */
	public static final String CMD_VERSION = "1.2.1";

	/* * * * * GUI constants * * * * */

	/** The GUI version. */
	public static final String GUI_VERSION = "1.2.1";
	/** The GUI application name. */
	public static final String APPLICATION_NAME = "iCloud calendar URLs";
	/** The copyright link string. */
	public static final String COPYRIGHT_LINK = "<a href='http://www.niftyside.com'>NiftySide - " + COPYRIGHT_NAME
			+ "</a>";

	/* * * * * Avoid instantiation * * * * */

	/**
	 * Avoids instantiation.
	 * 
	 * @since 1.2.1
	 */
	private AppConstants() {
		// nothing to do
	}
}

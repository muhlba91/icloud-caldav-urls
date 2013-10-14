package com.niftyside.icloud.calendars.app.gui.view

import com.niftyside.icloud.calendars.api.Calendars
import com.niftyside.icloud.calendars.app.gui.Application

import javax.swing.*
import java.awt.*
import java.awt.event.ActionEvent

/**
 * The about dialog.
 *
 * Project: iCloudCalendars
 * User: daniel
 * Date: 13.10.13
 * Time: 15:10
 *
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 *
 * @version 2.0.0
 */
class About extends JDialog {
	/* * * * * Variables * * * * */

	private static final def ABOUT_TEXT = "<html><br>" +
			"<b>Version: " + Application.VERSION +
			"</b><br>" +
			"<b>API version: " + Calendars.VERSION +
			"</b><br><br>" +
			"This application will help you with getting your <b>iCloud calendar URLs</b> to use them in third-party clients like Thunderbird.<br><br>" +
			"You are <b>allowed</b> to use this piece of software for your own personal use only.<br>" +
			"Furthermore, due to restrictions of Apple itself I must <b>prohibit</b>:<br>" +
			"<ul>" +
			"<li>You are <b>not allowed</b> to make any parts of the software <b>publicly available</b> or to <b>republish</b> them.</li>" +
			"<li>You are <b>not allowed</b> to <b>modify</b> any of the files and/or source code unless you agree with point 4.</li>" +
			"<li>You are <b>only allowed</b> to use it for your <b>personal use</b>.</li>" +
			"<li>If you are <b>modifying anything</b> to enhance this software you are required to <b>transfer your copyright to me</b> and hand out all your changes.</li>" +
			"</ul>" +
			"For <b>more information</b> about this app and the the license conditions visit <a href='http://icloud.niftyside.com'>http://icloud.niftyside.com</a>.<br>" +
			"As this application is freeware, please consider making a <i>small donation</i>! <i>Thank you!</i><br><br>" +
			"Copyright (C) " + Calendars.COPYRIGHT_YEARS + " by " + Application.COPYRIGHT_LINK + "</html>"

	/* * * * * Constructor * * * * */

	/**
	 * Creates a new about dialog.
	 *
	 * @param source
	 *            the source
	 *
	 * @since 2.0.0
	 */
	About(JFrame source) {
		super()

		setTitle("About")
		setDefaultCloseOperation(DISPOSE_ON_CLOSE)
		setLayout(new BorderLayout())

		addAboutLabel()
		addText()
		addCloseButton()

		pack()
		setLocationRelativeTo(source)
	}

	/* * * * * Private methods * * * * */

	/**
	 * Adds the about label.
	 *
	 * @since 2.0.0
	 */
	private def addAboutLabel() {
		def label = new JLabel("<html><br><b><font color='#009900'>About</font></b></html>");
		label.setHorizontalAlignment(SwingConstants.CENTER)
		label.setFont(new Font("Serif", Font.PLAIN, 27))

		add(label, BorderLayout.NORTH)
	}

	/**
	 * Adds the about text.
	 *
	 * @since 2.0.0
	 */
	private def addText() {
		def view = new JPanel()

		def msg = new JLabel(ABOUT_TEXT)
		msg.setHorizontalAlignment(SwingConstants.LEFT)
		msg.setFont(new Font("Serif", Font.PLAIN, 14))

		view.add(msg)
		add(view, BorderLayout.CENTER)
	}

	/**
	 * Adds the close button.
	 *
	 * @since 2.0.0
	 */
	private def addCloseButton() {
		def closePanel = new JPanel()
		def close = new JButton(new AbstractAction("Close") {
			@Override
			public void actionPerformed(final ActionEvent event) {
				dispose()
			}
		})

		closePanel.add(close)
		add(closePanel, BorderLayout.SOUTH)
	}
}

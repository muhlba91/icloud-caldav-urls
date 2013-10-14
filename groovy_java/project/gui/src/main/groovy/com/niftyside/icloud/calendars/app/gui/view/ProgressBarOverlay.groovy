package com.niftyside.icloud.calendars.app.gui.view

import javax.swing.*
import java.awt.*

/**
 * An indeterminate progress bar overlay.
 *
 * Project: iCloud calendars
 * User: daniel
 * Date: 12.10.13
 * Time: 17:46
 *
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 *
 * @version 2.0.0
 */
class ProgressBarOverlay extends JDialog {
	/* * * * * Constructor * * * * */

	/**
	 * Creates a new indeterminate progress bar.
	 *
	 * @param parent
	 *            the parent window
	 *
	 * @since 2.0.0
	 */
	ProgressBarOverlay(JFrame parent) {
		super()

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE)

		def progressBar = new JProgressBar()
		progressBar.setIndeterminate(true)
		progressBar.setPreferredSize(new Dimension(300, 30))

		add(progressBar)

		pack()
		setLocationRelativeTo(parent)
	}
}

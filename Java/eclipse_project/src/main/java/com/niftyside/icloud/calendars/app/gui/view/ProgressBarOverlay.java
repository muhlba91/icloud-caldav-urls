package com.niftyside.icloud.calendars.app.gui.view;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

/**
 * An indeterminate progress bar.
 * 
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 * 
 * @see http://icloud.niftyside.com
 * 
 * @version 1.2.1
 * 
 */
public class ProgressBarOverlay extends JDialog {
	/* * * * * Variables * * * * */

	private static final long serialVersionUID = 1L;

	/* * * * * Constructor * * * * */

	/**
	 * Creates a new indeterminate progress bar.
	 * 
	 * @param parent
	 *            the parent window
	 * 
	 * @since 1.1
	 */
	public ProgressBarOverlay(final JFrame parent) {
		super();

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		final JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setPreferredSize(new Dimension(300, 30));

		add(progressBar);

		pack();
		setLocationRelativeTo(parent);
	}
}

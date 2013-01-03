package com.niftyside.icloud.calendars.app.gui.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.niftyside.icloud.calendars.api.ICloud;
import com.niftyside.icloud.calendars.app.AppConstants;

/**
 * The About dialog.
 * 
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 * 
 * @see http://icloud.niftyside.com
 * 
 * @version 1.2.1
 * 
 */
public class AboutDialog extends JDialog {
	/* * * * * Variables * * * * */

	private static final long serialVersionUID = 1L;
	/** The about text. */
	private static final String ABOUT_TEXT = "<html><br>"
			+ "<b>Version: "
			+ AppConstants.GUI_VERSION
			+ "</b><br>"
			+ "<b>API version: "
			+ ICloud.VERSION
			+ "</b><br><br>"
			+ "This application will help you with getting your <b>iCloud calendar URLs</b><br>"
			+ "to use them in third-party clients like Thunderbird.<br><br>"
			+ "It is explicitly <b>prohibited</b> to:"
			+ "<ul>"
			+ "<li>publish this app or the source code</li>"
			+ "<li>modify this app or the source code</lu>"
			+ "</ul>"
			+ "For <b>more information</b> about this app and the the license conditions visit <a href='http://icloud.niftyside.com'>http://icloud.niftyside.com</a>.<br>"
			+ "As this application is freeware, please consider making a <i>small donation</i>! <i>Thank you!</i><br><br>"
			+ "Copyright (C) " + AppConstants.COPYRIGHT_YEARS + "by " + AppConstants.COPYRIGHT_LINK + "</html>";

	/* * * * * Constructor * * * * */

	/**
	 * Creates a new about dialog.
	 * 
	 * @param source
	 *            the source
	 * 
	 * @since 1.0
	 */
	public AboutDialog(final JFrame source) {
		super();

		setTitle("About");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		addAboutLabel();
		addText();
		addCloseButton();

		pack();
		setLocationRelativeTo(source);
	}

	/* * * * * Private methods * * * * */

	/**
	 * Adds the about text.
	 * 
	 * @since 1.1
	 */
	private void addText() {
		final JPanel view = new JPanel();
		view.setPreferredSize(new Dimension(625, 300));

		final JLabel msg = new JLabel(ABOUT_TEXT);
		msg.setHorizontalAlignment(SwingConstants.LEFT);
		msg.setFont(new Font("Serif", Font.PLAIN, 14));

		view.add(msg);
		add(view, BorderLayout.CENTER);
	}

	/**
	 * Adds the about label.
	 * 
	 * @since 1.1
	 */
	private void addAboutLabel() {
		final JLabel label = new JLabel("<html><b><font color='#009900'>About</font></b></html>");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Serif", Font.PLAIN, 27));

		add(label, BorderLayout.NORTH);
	}

	/**
	 * Adds the close button.
	 * 
	 * @since 1.1
	 */
	private void addCloseButton() {
		final JPanel closePanel = new JPanel();
		final JButton close = new JButton(new AbstractAction("Close") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				dispose();
			}
		});

		closePanel.add(close);
		add(closePanel, BorderLayout.SOUTH);
	}
}

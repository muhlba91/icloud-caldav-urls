package com.niftyside.icloud.calendars.app.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.niftyside.icloud.calendars.app.MainApp;

/**
 * The About dialog.
 * 
 * @author Daniel Muehlbachler
 * @copyright 2012 Daniel Muehlbachler
 * 
 * @see {@link http://icloud.niftyside.com}
 * 
 * @version 1.0
 * 
 */
public class AboutDialog extends JDialog {
	/* * * * * Variables * * * * */

	private static final long serialVersionUID = 2381243535251478094L;
	/** The close handler. */
	private final ActionListener closeHdl = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent event) {
			dispose();
		}
	};

	/* * * * * Constructor * * * * */

	/**
	 * Creates a new about dialog.
	 * 
	 * @param source
	 *            the source
	 * 
	 * @since 1.0
	 */
	public AboutDialog(JFrame source) {
		// about text
		String about = "<html><br>"
				+ "<b>Version: "
				+ MainApp.VERSION
				+ "</b><br><br>"
				+ "This application will help you with getting your <b>iCloud calendar URLs</b><br>"
				+ "to use them in third-party clients like Thunderbird.<br><br>"
				+ "It is explicitly <b>prohibited</b> to:"
				+ "<ul>"
				+ "<li>publish this app or the source code</li>"
				+ "<li>modify this app or the source code</lu>"
				+ "</ul>"
				+ "For <b>more information</b> about this app and the the license conditions visit <a href='http://icloud.niftyside.com'>http://icloud.niftyside.com</a>.<br>"
				+ "As this application is freeware, please consider making a <i>little donation</i>! <i>Thank you!</i><br><br>"
				+ "Copyright (C) 2012 by <a href='http://www.niftyside.com'>NiftySide - Daniel Mühlbachler</a> (http://www.niftyside.com)";

		// create window
		JPanel main = new JPanel();
		main.setLayout(new BorderLayout());
		add(main);

		// label
		JPanel labelView = new JPanel();
		// set message
		JLabel label = new JLabel(
				"<html><b><font color='#009900'>About</font></b></html>");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Serif", Font.PLAIN, 27));
		labelView.add(label);
		// add view
		main.add(labelView, BorderLayout.NORTH);

		// text
		JPanel view = new JPanel();
		view.setPreferredSize(new Dimension(625, 300));
		// set message
		JLabel msg = new JLabel(about);
		msg.setHorizontalAlignment(SwingConstants.LEFT);
		msg.setFont(new Font("Serif", Font.PLAIN, 14));
		view.add(msg);
		// add view
		main.add(view, BorderLayout.CENTER);

		// close button
		JPanel closePanel = new JPanel();
		JButton close = new JButton("Close");
		closePanel.add(close);
		main.add(closePanel, BorderLayout.SOUTH);
		// add action
		close.addActionListener(closeHdl);

		// dialog specific settings
		setTitle("About");
		setSize(new Dimension(250, 200));
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(source);
	}
}

package com.niftyside.icloud.calendars.app.views;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import com.niftyside.icloud.calendars.app.events.CredentialEvent;
import com.niftyside.icloud.calendars.app.listeners.CredentialListener;

/**
 * The main view of the application.
 * 
 * @author Daniel Muehlbachler
 * @copyright 2012 Daniel Muehlbachler
 * 
 * @see {@link http://icloud.niftyside.com}
 * 
 * @version 1.0
 * 
 */
public class FormView extends JPanel {
	/* * * * * Variables * * * * */

	private static final long serialVersionUID = -3226185707985963888L;
	/** The action listeners. */
	List<CredentialListener> listeners;
	/** The Apple-ID field. */
	private final JTextField appleID;
	/** The password field. */
	private final JTextField password;
	/** The server combo box. */
	private final JComboBox<String> server;
	/** The fetch button. */
	private final JButton fetch;
	/** Key event. */
	private final AbstractAction performFetch = new AbstractAction() {
		private static final long serialVersionUID = 9028472314559559970L;

		@Override
		public void actionPerformed(ActionEvent e) {
			fetch.doClick();
		}
	};
	/** Send credentials action. */
	private final ActionListener sendHdl = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			fireEvent();
		}
	};

	/* * * * * Constructor * * * * */

	/**
	 * Creates a new form.
	 * 
	 * @since 1.0
	 */
	public FormView() {
		listeners = new ArrayList<CredentialListener>();

		// servers
		String[] servers = { "p01-caldav.icloud.com", "p02-caldav.icloud.com",
				"p03-caldav.icloud.com", "p04-caldav.icloud.com",
				"p05-caldav.icloud.com", "p06-caldav.icloud.com",
				"p07-caldav.icloud.com", "p08-caldav.icloud.com" };

		// main panel for layouting the objects
		JPanel main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
		add(main);

		// input field panel
		JPanel formPanel = new JPanel();
		formPanel.setBorder(BorderFactory.createEmptyBorder(5, 25, 5, 25));
		// box layouts for left and right
		JPanel s1Panel = new JPanel();
		s1Panel.setLayout(new BoxLayout(s1Panel, BoxLayout.Y_AXIS));
		JPanel s2Panel = new JPanel();
		s2Panel.setLayout(new BoxLayout(s2Panel, BoxLayout.Y_AXIS));
		formPanel.add(s1Panel);
		formPanel.add(s2Panel);

		JLabel appleIDLabel = new JLabel("Apple ID: ");
		JLabel passwordLabel = new JLabel("Password: ");
		JLabel serverLabel = new JLabel("Server: ");
		appleID = new JTextField();
		appleID.setColumns(50);
		password = new JPasswordField();
		password.setColumns(50);
		server = new JComboBox<String>(servers);

		// left with gaps / rigid areas
		s1Panel.add(appleIDLabel);
		s1Panel.add(Box.createRigidArea(new Dimension(0, 10)));
		s1Panel.add(passwordLabel);
		s1Panel.add(Box.createRigidArea(new Dimension(0, 10)));
		s1Panel.add(serverLabel);

		// right with gaps / rigid areas
		s2Panel.add(appleID);
		s2Panel.add(Box.createRigidArea(new Dimension(0, 5)));
		s2Panel.add(password);
		s2Panel.add(Box.createRigidArea(new Dimension(0, 5)));
		s2Panel.add(server);

		// add panel
		main.add(formPanel);

		// button panel
		JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
		fetch = new JButton("Get calendar URLs");
		bottom.add(fetch);
		fetch.addActionListener(sendHdl);

		// add panel
		main.add(bottom);

		// set size
		setPreferredSize(new Dimension(500, 140));

		// set misc actions
		addKeyEvents();
		appleID.requestFocus();
	}

	/* * * * * Methods * * * * */

	/**
	 * Adds a new {@link CredentialListener}.
	 * 
	 * @param l
	 *            the listener
	 * 
	 * @since 1.0
	 */
	public void addCredentialListener(CredentialListener l) {
		listeners.add(l);
	}

	/**
	 * Removes a {@link CredentialListener}.
	 * 
	 * @param l
	 *            the listener
	 * 
	 * @since 1.0
	 */
	public void removeCredentialListener(CredentialListener l) {
		listeners.remove(l);
	}

	/* * * * * Private methods * * * * */

	/**
	 * Fires a {@link CredentialEvent} to all listeners.
	 * 
	 * @since 1.0
	 */
	private void fireEvent() {
		CredentialEvent e = new CredentialEvent(this, appleID.getText(),
				password.getText(), (String) server.getSelectedItem());
		for (CredentialListener l : listeners)
			l.credentialsSent(e);
	}

	/**
	 * Adds key events to all necessary fields.
	 * 
	 * @since 1.0
	 */
	private void addKeyEvents() {
		InputMap ip1 = appleID.getInputMap();
		InputMap ip2 = password.getInputMap();

		ActionMap am1 = appleID.getActionMap();
		ActionMap am2 = password.getActionMap();

		// ENTER
		KeyStroke enter = KeyStroke.getKeyStroke((char) KeyEvent.VK_ENTER);
		am1.put("enter", performFetch);
		am2.put("enter", performFetch);
		ip1.put(enter, "enter");
		ip2.put(enter, "enter");
	}
}

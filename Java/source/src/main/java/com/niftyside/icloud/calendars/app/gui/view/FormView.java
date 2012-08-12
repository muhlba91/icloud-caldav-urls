package com.niftyside.icloud.calendars.app.gui.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import com.niftyside.icloud.calendars.app.gui.event.CredentialEvent;
import com.niftyside.icloud.calendars.app.gui.listener.CredentialListener;

/**
 * The form of the application.
 * 
 * @author Daniel Muehlbachler
 * @copyright 2012 Daniel Muehlbachler
 * 
 * @see http://icloud.niftyside.com
 * 
 * @version 1.1
 * 
 */
public class FormView extends JComponent {
	/* * * * * Variables * * * * */

	private static final long serialVersionUID = 1L;
	/** The servers. */
	private static final String[] servers = { "p01-caldav.icloud.com",
			"p02-caldav.icloud.com", "p03-caldav.icloud.com",
			"p04-caldav.icloud.com", "p05-caldav.icloud.com",
			"p06-caldav.icloud.com", "p07-caldav.icloud.com",
			"p08-caldav.icloud.com" };
	/** The credential listeners. */
	private final List<CredentialListener> listeners;
	/** The Apple-ID field. */
	private final JTextField appleID;
	/** The password field. */
	private final JPasswordField password;
	/** The server combo box. */
	private final JComboBox<String> server;

	/* * * * * Constructor * * * * */

	public FormView() {
		listeners = new ArrayList<>();

		appleID = new JTextField();
		appleID.setColumns(50);
		password = new JPasswordField();
		password.setColumns(50);
		server = new JComboBox<String>(servers);

		setPreferredSize(new Dimension(500, 140));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		addFormPanel();
		addBottomPanel();

		addKeyEvents();

		appleID.requestFocus();
	}

	/* * * * * Methods * * * * */

	/**
	 * Adds a new {@link CredentialListener}.
	 * 
	 * @param listener
	 *            the listener
	 * 
	 * @since 1.1
	 */
	public void addCredentialListener(final CredentialListener listener) {
		listeners.add(listener);
	}

	/**
	 * Removes a {@link CredentialListener}.
	 * 
	 * @param listener
	 *            the listener
	 * 
	 * @since 1.1
	 */
	public void removeCredentialListener(final CredentialListener listener) {
		listeners.remove(listener);
	}

	/* * * * * Private methods * * * * */

	/**
	 * Adds the form panel.
	 * 
	 * @since 1.1
	 */
	private void addFormPanel() {
		final JPanel formPanel = new JPanel();
		formPanel.setBorder(BorderFactory.createEmptyBorder(5, 25, 5, 25));

		formPanel.add(createLabelPanel());
		formPanel.add(createFieldPanel());

		add(formPanel);
	}

	/**
	 * Creates the label panel.
	 * 
	 * @return the label panel
	 * 
	 * @since 1.1
	 */
	private Component createLabelPanel() {
		final JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));

		labelPanel.add(new JLabel("Apple ID: "));
		labelPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		labelPanel.add(new JLabel("Password: "));
		labelPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		labelPanel.add(new JLabel("Server: "));

		return labelPanel;
	}

	/**
	 * Creates the input field panel.
	 * 
	 * @return the input panel
	 * 
	 * @since 1.1
	 */
	private JPanel createFieldPanel() {
		final JPanel fieldPanel = new JPanel();
		fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));

		fieldPanel.add(appleID);
		fieldPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		fieldPanel.add(password);
		fieldPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		fieldPanel.add(server);

		return fieldPanel;
	}

	/**
	 * Adds the bottom panel.
	 * 
	 * @since 1.1
	 */
	private void addBottomPanel() {
		final JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
		final JButton fetch = new JButton(new AbstractAction(
				"Get calendar URLs") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				fireCredentialEvent();
			}
		});

		bottom.add(fetch);
		add(bottom);
	}

	/**
	 * Fires a new {@link CredentialEvent} to all listeners.
	 * 
	 * @since 1.1
	 */
	private void fireCredentialEvent() {
		final CredentialEvent e = new CredentialEvent(appleID.getText(),
				new String(password.getPassword()),
				(String) server.getSelectedItem());
		for (final CredentialListener l : listeners)
			l.credentialsSent(e);
	}

	/**
	 * Adds keyboard events.
	 * 
	 * @since 1.1
	 */
	private void addKeyEvents() {
		final Action fetch = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				fireCredentialEvent();
			}
		};

		final InputMap inputMapID = appleID.getInputMap();
		final InputMap inputMapPW = password.getInputMap();

		final ActionMap actionMapID = appleID.getActionMap();
		final ActionMap actionMapPW = password.getActionMap();

		final KeyStroke enter = KeyStroke
				.getKeyStroke((char) KeyEvent.VK_ENTER);
		actionMapID.put("enter", fetch);
		actionMapPW.put("enter", fetch);
		inputMapID.put(enter, "enter");
		inputMapPW.put(enter, "enter");
	}
}

package com.niftyside.icloud.calendars.app.gui.view

import com.niftyside.icloud.calendars.api.Calendars
import com.niftyside.icloud.calendars.app.gui.event.CredentialEvent
import com.niftyside.icloud.calendars.app.gui.listener.CredentialListener

import javax.swing.*
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import java.util.concurrent.CopyOnWriteArrayList

/**
 * The input form.
 *
 * Project: iCloudCalendars
 * User: daniel
 * Date: 13.10.13
 * Time: 15:25
 *
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 *
 * @version 2.0.1
 */
class Form extends JComponent {
	/* * * * * Variables * * * * */

	private static final ENTER_KEY = "enter"
	private final credentialListeners
	private final appleIdField
	private final passwordField
	private final serverComboBox

	/* * * * * Constructor * * * * */

	/**
	 * Creates a new form view pane.
	 *
	 * @since 2.0.0
	 */
	Form() {
		super()

		credentialListeners = new CopyOnWriteArrayList<CredentialListener>()

		appleIdField = new JTextField()
		appleIdField.setColumns(50)
		passwordField = new JPasswordField()
		passwordField.setColumns(50)
		serverComboBox = new JComboBox<String>(Calendars.SERVERS)

		setPreferredSize(new Dimension(625, 175))
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS))

		addFormPanel()
		addBottomPanel()

		addKeyEvents()

		appleIdField.requestFocus()
	}

	/* * * * * Methods * * * * */

	/**
	 * Adds a new {@link CredentialListener}.
	 *
	 * @param listener
	 *            the listener
	 *
	 * @since 2.0.0
	 */
	def addCredentialListener(CredentialListener listener) {
		credentialListeners.add(listener)
	}

	/**
	 * Removes a {@link CredentialListener}.
	 *
	 * @param listener
	 *            the listener
	 *
	 * @since 2.0.0
	 */
	def removeCredentialListener(CredentialListener listener) {
		credentialListeners.remove(listener)
	}

	/* * * * * Private methods * * * * */

	/**
	 * Adds the form panel.
	 *
	 * @since 2.0.0
	 */
	private addFormPanel() {
		def formPanel = new JPanel(new GridBagLayout())
		formPanel.setBorder(BorderFactory.createEmptyBorder(5, 25, 5, 25))

		addRowToPanel(formPanel, "Apple ID:", appleIdField, 0)
		addRowToPanel(formPanel, "Password:", passwordField, 1)
		addRowToPanel(formPanel, "Server:", serverComboBox, 2)

		add(formPanel)
	}

	/**
	 * Adds a new input row to a panel.
	 *
	 * @param panel
	 * 				the panel to add to
	 * @param title
	 * 				the label title
	 * @param component
	 * 				the component to add
	 * @param row
	 * 				the row number
	 *
	 * @since 2.0.0
	 */
	private addRowToPanel(JPanel panel, String title, JComponent component, int row) {
		panel.add(new JLabel(title), createGridBagConstraints(row, 0, false))
		panel.add(component, createGridBagConstraints(row, 1, true))
	}

	/**
	 * Creates new constraints for the layout.
	 *
	 * @param row
	 * 			the row
	 * @param col
	 * 			the column
	 * @param isLast
	 * 			true - it is the last element in this row; false - otherwise
	 *
	 * @return
	 * the generated constraints
	 *
	 * @since 2.0.0
	 */
	private createGridBagConstraints(int row, int col, boolean isLast) {
		def constraints = new GridBagConstraints()

		constraints.with {
			gridx = col
			gridy = row
			anchor = NORTHWEST
			fill = isLast ? HORIZONTAL : NONE
			gridwidth = isLast ? REMAINDER : 1
			weightx = isLast ? 10.0 : 1.0
			weighty = 1.0
			insets = new Insets(5, 5, 5, 5)
		}

		constraints
	}

	/**
	 * Adds the bottom panel.
	 *
	 * @since 2.0.0
	 */
	private addBottomPanel() {
		def bottom = new JPanel(new FlowLayout(FlowLayout.CENTER))
		def fetch = new JButton(new AbstractAction("Get calendar URLs") {
			@Override
			void actionPerformed(ActionEvent event) {
				fireCredentialEvent()
			}
		})

		bottom.add(fetch)
		add(bottom)
	}

	/**
	 * Fires a new {@link CredentialEvent} to all listeners.
	 *
	 * @since 2.0.0
	 */
	private fireCredentialEvent() {
		def event = new CredentialEvent(appleIdField.text, new String(passwordField.password), (String) serverComboBox.selectedItem)
		credentialListeners.each {
			it.credentialsSent(event)
		}
	}

	/**
	 * Adds keyboard events.
	 *
	 * @since 2.0.0
	 */
	private addKeyEvents() {
		def fetch = { ActionEvent event ->
			fireCredentialEvent()
		} as AbstractAction

		def inputMapID = appleIdField.inputMap
		def inputMapPW = passwordField.inputMap

		def actionMapID = appleIdField.actionMap
		def actionMapPW = passwordField.actionMap

		def enter = KeyStroke.getKeyStroke((char) KeyEvent.VK_ENTER)
		actionMapID.put(ENTER_KEY, fetch)
		actionMapPW.put(ENTER_KEY, fetch)
		inputMapID.put(enter, ENTER_KEY)
		inputMapPW.put(enter, ENTER_KEY)
	}
}

package com.niftyside.icloud.calendars.app.gui.model

import com.niftyside.icloud.calendars.api.model.UserData

import javax.swing.table.AbstractTableModel

/**
 * The data model for the results table.
 *
 * Project: iCloudCalendars
 * User: daniel
 * Date: 13.10.13
 * Time: 17:51
 *
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 *
 * @version 2.0.0
 */
class ResultTableModel extends AbstractTableModel {
	/* * * * * Variables * * * * */

	private def userData
	private def message

	/* * * * * Constructor * * * * */

	/**
	 * Creates a new table model.
	 *
	 * @since 2.0.0
	 */
	ResultTableModel() {
		super()

		userData = null
		message = ""
	}

	/* * * * * Methods * * * * */

	/**
	 * Sets new {@link UserData}.
	 *
	 * @param userData
	 *            the new data
	 *
	 * @since 2.0.0
	 */
	def setUserData(UserData userData) {
		this.userData = userData
		message = null
	}

	/**
	 * Sets a new message.
	 *
	 * @param message
	 *            the new message
	 *
	 * @since 2.0.0
	 */
	def setMessage(String message) {
		this.message = message
		userData = null
	}

	@Override
	int getRowCount() {
		message == null ? userData.getCalendars().size() + 2 : 1
	}

	@Override
	int getColumnCount() {
		message == null ? 3 : 1
	}

	@Override
	Object getValueAt(int rowIndex, int columnIndex) {
		def value = message

		if(value == null) {
			def calendar = (rowIndex >= 2) ? userData.getCalendars().get(rowIndex - 2) : null
			switch(columnIndex) {
				case 0:
					value = calendar != null ? calendar.getName() : (rowIndex == 0 ? "Principal" : "CardDAV")
					break
				case 1:
					value = calendar != null ? calendar.getHref() : "N/A"
					break
				case 2:
					value = calendar != null ? calendar.getURL() : (rowIndex == 0 ? userData.getPrincipalUrl() : userData.getCardDavUrl())
					break
				default:
					value = ""
			}
		}

		value
	}
}

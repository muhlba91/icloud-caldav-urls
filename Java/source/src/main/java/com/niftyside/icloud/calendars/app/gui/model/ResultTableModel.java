package com.niftyside.icloud.calendars.app.gui.model;

import javax.swing.table.AbstractTableModel;

import com.niftyside.icloud.calendars.api.model.Calendar;
import com.niftyside.icloud.calendars.api.model.UserData;

/**
 * The data model for the result table.
 * 
 * @author Daniel Muehlbachler
 * @copyright 2012 Daniel Muehlbachler
 * 
 * @see http://icloud.niftyside.com
 * 
 * @version 1.1
 * 
 */
public class ResultTableModel extends AbstractTableModel {
	/* * * * * Variables * * * * */

	private static final long serialVersionUID = 1L;
	/** The user data. */
	private UserData userData;
	/** The message. */
	private String message;

	/* * * * * Constructor * * * * */

	/**
	 * Creates a new table model.
	 * 
	 * @since 1.1
	 */
	public ResultTableModel() {
		userData = null;
		message = "";
	}

	/* * * * * Methods * * * * */

	/**
	 * Sets new {@link UserData}.
	 * 
	 * @param userData
	 *            the new data
	 * 
	 * @since 1.1
	 */
	public void setUserData(final UserData userData) {
		this.userData = userData;
		message = null;
	}

	/**
	 * Sets a new message.
	 * 
	 * @param message
	 *            the new message
	 * 
	 * @since 1.1
	 */
	public void setMessage(final String message) {
		this.message = message;
		userData = null;
	}

	@Override
	public int getColumnCount() {
		return (message == null) ? 3 : 1;
	}

	@Override
	public int getRowCount() {
		return (message == null) ? userData.getCalendars().size() : 1;
	}

	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		String value = message;

		if (value == null) {
			final Calendar calendar = userData.getCalendars().get(rowIndex);
			switch (columnIndex) {
				case 0:
					value = calendar.getName();
					break;
				case 1:
					value = calendar.getHref();
					break;
				case 2:
					value = calendar.getURL();
					break;
			}
		}

		return value;
	}
}

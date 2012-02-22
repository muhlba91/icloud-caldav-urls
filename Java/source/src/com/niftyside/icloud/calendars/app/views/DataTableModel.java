package com.niftyside.icloud.calendars.app.views;

import java.io.UnsupportedEncodingException;

import javax.swing.table.AbstractTableModel;

import com.niftyside.icloud.calendars.handlers.CalendarInfo;
import com.niftyside.icloud.calendars.handlers.Data;

/**
 * The data model for the result view table.
 * 
 * @author Daniel Muehlbachler
 * @copyright 2012 Daniel Muehlbachler
 * 
 * @see {@link http://icloud.niftyside.com}
 * 
 * @version 1.0
 * 
 */
public class DataTableModel extends AbstractTableModel {
	/* * * * * Variables * * * * */

	private static final long serialVersionUID = -1968189014804518294L;
	/** The data. */
	Data data;
	/** The message. */
	String message;

	/* * * * * Constructor * * * * */

	/**
	 * Creates a new {@link Data} table model.
	 * 
	 * @since 1.0
	 */
	public DataTableModel() {
		data = null;
		message = "";
	}

	/* * * * * Methods * * * * */

	/**
	 * Sets a message in the first row.
	 * 
	 * @param msg
	 *            the message
	 * 
	 * @since 1.0
	 */
	public void setMessage(String msg) {
		message = msg;
		data = null;
	}

	/**
	 * Sets a new {@link Data}.
	 * 
	 * @param data
	 *            the new data
	 * 
	 * @since 1.0
	 */
	public void setData(Data data) {
		message = null;
		this.data = data;
	}

	@Override
	public int getColumnCount() {
		return message == null ? 3 : 1;
	}

	@Override
	public int getRowCount() {
		return message == null ? data.getCalendars().size() : 1;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		String obj = message;

		// retrieve calendar
		if (obj == null) {
			CalendarInfo cal = data.getCalendars().get(arg0);
			switch (arg1) {
			case 0:
				obj = cal.getName();
				break;
			case 1:
				obj = cal.getHref();
				break;
			case 2:
				obj = cal.getURL();
				break;
			}
		}

		// make human readable... :-)
		byte[] utf8 = obj.getBytes();
		try {
			obj = new String(utf8, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}

		return obj;
	}

}

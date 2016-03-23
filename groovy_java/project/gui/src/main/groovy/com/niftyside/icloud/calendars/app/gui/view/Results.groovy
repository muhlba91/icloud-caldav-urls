package com.niftyside.icloud.calendars.app.gui.view

import com.niftyside.icloud.calendars.app.gui.event.UserDataEvent
import com.niftyside.icloud.calendars.app.gui.listener.UserDataListener
import com.niftyside.icloud.calendars.app.gui.model.ResultTableModel

import javax.swing.*
import javax.swing.table.TableColumn
import java.awt.*
import java.awt.datatransfer.StringSelection
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent

/**
 * Displays all available results/information.
 *
 * Project: iCloudCalendars
 * User: daniel
 * Date: 13.10.13
 * Time: 16:28
 *
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 *
 * @version 2.0.0
 */
class Results extends JComponent {
	/* * * * * Variables * * * * */

	private final table
	private final tableModel

	/* * * * * Constructor * * * * */

	/**
	 * Creates a new result view pane.
	 *
	 * @since 2.0.0
	 */
	Results() {
		super()
		setLayout(new BorderLayout())

		table = new JTable()
		tableModel = new ResultTableModel()
		table.setModel(tableModel)
		layoutTable()

		setData(null)

		setCopy()

		setSize(table.preferredSize)
		setPreferredSize(table.preferredSize)
	}

	/* * * * * Methods * * * * */

	/**
	 * Gets a new {@link UserDataListener}.
	 *
	 * @return a new user data listener
	 *
	 * @since 2.0.0
	 */
	def getUserDataListener() {
		{ UserDataEvent event ->
			setData(event)
		} as UserDataListener
	}

	/* * * * * Private methods * * * * */

	/**
	 * Layouts the table.
	 *
	 * @since 2.0.0
	 */
	private layoutTable() {
		table.setPreferredSize(new Dimension(975, 225))
		table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS)

		table.tableHeader.setBackground(Color.LIGHT_GRAY)

		add(new JScrollPane(table), BorderLayout.CENTER)
	}

	/**
	 * Displays the fetched {@link UserData} or error message.
	 *
	 * @param event
	 *            the event
	 *
	 * @since 2.0.0
	 */
	private setData(UserDataEvent event) {
		makeSingleColumn()

		if(event == null) {
			tableModel.setMessage("Please submit your credentials first!")
		} else if(event.valid) {
			makeColumns()
			tableModel.setUserData(event.userData)
		} else {
			tableModel.setMessage(event.exception.message)
		}

		table.revalidate()
	}

	/**
	 * Deletes all columns.
	 *
	 * @since 2.0.0
	 */
	private deleteColumns() {
		def columnModel = table.tableHeader.columnModel
		for(int i = columnModel.columnCount - 1; i >= 0; i--) {
			columnModel.removeColumn(columnModel.getColumn(i))
		}
	}

	/**
	 * Makes one single column.
	 *
	 * @since 2.0.0
	 */
	private makeSingleColumn() {
		deleteColumns()

		def col0 = new TableColumn(0)
		col0.setHeaderValue("Information - Error")
		table.tableHeader.columnModel.addColumn(col0)
	}

	/**
	 * Makes three columns.
	 *
	 * @since 2.0.0
	 */
	private makeColumns() {
		deleteColumns()
		def columnModel = table.tableHeader.columnModel

		def col0 = new TableColumn(0)
		col0.setHeaderValue("Name")
		columnModel.addColumn(col0)

		def col1 = new TableColumn(1)
		col1.setHeaderValue("href")
		col1.setPreferredWidth(250)
		columnModel.addColumn(col1)

		def col2 = new TableColumn(2)
		col2.setHeaderValue("URL")
		col2.setPreferredWidth(375)
		columnModel.addColumn(col2)
	}

	/**
	 * Sets the copy handler for the table.
	 *
	 * @since 2.0.0
	 */
	private setCopy() {
		def key = KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK, false)
		table.registerKeyboardAction({ ActionEvent event ->
			def col = table.selectedColumn
			def row = table.selectedRow

			if(col != -1 && row != -1) {
				def val = table.getValueAt(row, col)
				def data = (val == null) ? "" : val.toString()
				def selection = new StringSelection(data)
				Toolkit.defaultToolkit.systemClipboard.setContents(selection, selection)
			}
		} as AbstractAction, "copy", key, WHEN_FOCUSED)
	}
}

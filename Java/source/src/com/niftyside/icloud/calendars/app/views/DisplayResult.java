package com.niftyside.icloud.calendars.app.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.niftyside.icloud.calendars.handlers.Data;

/**
 * Displays all results regarding the submitted credentials.
 * 
 * @author Daniel Muehlbachler
 * @copyright 2012 Daniel Muehlbachler
 * 
 * @see {@link http://icloud.niftyside.com}
 * 
 * @version 1.0
 * 
 */
public class DisplayResult extends JPanel {
	/* * * * * Variables * * * * */

	private static final long serialVersionUID = -7686286472937828573L;
	/** The table model. */
	private final DataTableModel model;
	/** The table. */
	private final JTable table;
	/** The column model. */
	private final TableColumnModel cols;
	/** Copy listener. */
	private final ActionListener copyHdl = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			int col = table.getSelectedColumn();
			int row = table.getSelectedRow();

			if (col != -1 && row != -1) {
				Object val = table.getValueAt(row, col);
				String data = val == null ? "" : val.toString();
				final StringSelection selection = new StringSelection(data);
				final Clipboard clipboard = Toolkit.getDefaultToolkit()
						.getSystemClipboard();
				clipboard.setContents(selection, selection);
			}
		}
	};

	/* * * * * Constructor * * * * */

	/**
	 * Creates a new result view pane.
	 * 
	 * @since 1.0
	 */
	public DisplayResult() {
		JPanel main = new JPanel(new BorderLayout());

		// table
		table = new JTable();
		model = new DataTableModel();
		table.setModel(model);
		table.setPreferredSize(new Dimension(975, 225));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		// header
		JTableHeader header = table.getTableHeader();
		header.setBackground(Color.LIGHT_GRAY);
		// columns
		cols = header.getColumnModel();
		JScrollPane pane = new JScrollPane(table);
		pane.setPreferredSize(new Dimension(975, 225));
		main.add(pane);

		add(main);
		setSize(new Dimension(1000, 250));
		setData(null, null);

		// add copy listener
		final KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_C,
				ActionEvent.CTRL_MASK, false);
		table.registerKeyboardAction(copyHdl, "copy", key,
				JComponent.WHEN_FOCUSED);
	}

	/* * * * * Methods * * * * */

	/**
	 * Displays the fetched {@link Data} or error message.
	 * 
	 * @param data
	 *            the data or null
	 * @param e
	 *            the exception or null
	 * 
	 * @since 1.0
	 */
	public void setData(Data data, Exception e) {
		makeSingleColumn();

		if (data == null && e == null)
			model.setMessage("Please submit you credentials first!");
		else if (e != null)
			model.setMessage(e.getMessage());
		else {
			makeColumns();
			model.setData(data);
		}

		// reload data
		table.revalidate();
	}

	/* * * * * Private methods * * * * */

	/**
	 * Deletes all columns.
	 * 
	 * @since 1.0
	 */
	private void deleteColumns() {
		for (int i = cols.getColumnCount() - 1; i >= 0; i--)
			cols.removeColumn(cols.getColumn(i));
	}

	/**
	 * Makes one column.
	 * 
	 * @since 1.0
	 */
	private void makeSingleColumn() {
		deleteColumns();
		// col 0
		TableColumn col0 = new TableColumn(0);
		col0.setHeaderValue("Information - Error");
		cols.addColumn(col0);
	}

	/**
	 * Makes three columns.
	 * 
	 * @since 1.0
	 */
	private void makeColumns() {
		deleteColumns();
		// col 0
		TableColumn col0 = new TableColumn(0);
		col0.setHeaderValue("Calendar");
		cols.addColumn(col0);
		// col1
		TableColumn col1 = new TableColumn(1);
		col1.setHeaderValue("Calendar href");
		col1.setPreferredWidth(250);
		cols.addColumn(col1);
		// col2
		TableColumn col2 = new TableColumn(2);
		col2.setHeaderValue("Calendar URL");
		col2.setPreferredWidth(375);
		cols.addColumn(col2);
	}
}

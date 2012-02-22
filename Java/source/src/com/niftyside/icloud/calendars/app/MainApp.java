package com.niftyside.icloud.calendars.app;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import com.niftyside.icloud.calendars.app.events.CredentialEvent;
import com.niftyside.icloud.calendars.app.listeners.CredentialListener;
import com.niftyside.icloud.calendars.app.views.AboutDialog;
import com.niftyside.icloud.calendars.app.views.DisplayResult;
import com.niftyside.icloud.calendars.app.views.FormView;
import com.niftyside.icloud.calendars.exceptions.FactoryException;
import com.niftyside.icloud.calendars.exceptions.RequestException;
import com.niftyside.icloud.calendars.exceptions.ServerException;
import com.niftyside.icloud.calendars.exceptions.XMLException;
import com.niftyside.icloud.calendars.handlers.Data;
import com.niftyside.icloud.calendars.handlers.iCloudCalendars;

/**
 * Main application for iCloud calendars.
 * 
 * @author Daniel Muehlbachler
 * @copyright 2012 Daniel Muehlbachler
 * 
 * @see {@link http://icloud.niftyside.com}
 * 
 * @version 1.0
 * 
 */
public class MainApp {
	/* * * * * Variables * * * * */

	/** Version. */
	public final static String VERSION = "1.0";
	/** Application name. */
	private final static String APPLICATION_NAME = "iCloud calendar URLs";
	/** iCloud calendar URL fetcher handler. */
	private iCloudCalendars handler;
	/** Main window. */
	private JFrame window;
	/** Main content pane of the window. */
	private Container mainContentPane;
	/** The menu bar. */
	private JMenuBar menuBar;
	/** The file menu. */
	private JMenu fileMenu;
	/** The display view. */
	private DisplayResult resultView;
	/** The exit handler. */
	private final ActionListener exitHdl = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			exit();
		}
	};
	/** The about handler. */
	private final ActionListener aboutHdl = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			AboutDialog about = new AboutDialog(window);
			about.setVisible(true);
		}
	};
	/** The credential listener. */
	private final CredentialListener credListener = new CredentialListener() {
		@Override
		public void credentialsSent(CredentialEvent e) {
			Data data = null;
			Exception error = null;

			// get data
			handler.setCredentials(e.getAppleID(), e.getPassword());
			handler.setServer(e.getServer());
			try {
				data = handler.getData();
			} catch (RequestException | FactoryException | XMLException
					| ServerException e1) {
				error = e1;
			}
			handler.clearCredentials();

			// submit data to view
			resultView.setData(data, error);
		}
	};
	/** The window handler. */
	private final WindowListener windowHdl = new WindowListener() {
		@Override
		public void windowOpened(WindowEvent e) {
		}

		@Override
		public void windowClosing(WindowEvent e) {
			exit();
		}

		@Override
		public void windowClosed(WindowEvent e) {
			exit();
		}

		@Override
		public void windowIconified(WindowEvent e) {
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
		}

		@Override
		public void windowActivated(WindowEvent e) {
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
		}
	};

	/* * * * * Constructor * * * * */

	/**
	 * Creates a new application window.
	 * 
	 * @since 1.0
	 */
	public MainApp() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

		// initialization
		try {
			handler = new iCloudCalendars();
		} catch (RequestException e) {
			exit(e);
		}

		makeWindow();
	}

	/* * * * * Main method * * * * */

	/**
	 * The main method.
	 * 
	 * @param args
	 *            command line arguments...
	 * 
	 * @since 1.0
	 */
	public static void main(String[] args) {
		// disable INFO logging!
		System.setProperty("org.apache.commons.logging.Log",
				"org.apache.commons.logging.impl.SimpleLog");
		System.setProperty("org.apache.commons.logging.simplelog.showdatetime",
				"true");
		System.setProperty(
				"org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient",
				"fatal");

		MainApp app = new MainApp();
		app.display();
	}

	/* * * * * Methods * * * * */

	/**
	 * Displays the application.
	 * 
	 * @since 1.0
	 */
	public void display() {
		// show window/application
		window.pack();
		window.setLocation(50, 50);
		window.setVisible(true);
	}

	/* * * * * Private methods * * * * */

	/**
	 * Makes the window.
	 * 
	 * @since 1.0
	 */
	private void makeWindow() {
		// window
		window = new JFrame(APPLICATION_NAME);
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		window.addWindowListener(windowHdl);

		// menu

		// bar
		menuBar = new JMenuBar();
		window.setJMenuBar(menuBar);

		// file menu
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(fileMenu);
		// exit
		ImageIcon aboutIcon = new ImageIcon(getClass().getResource("about.png"));
		JMenuItem aboutMI = new JMenuItem("About", aboutIcon);
		aboutMI.setMnemonic('a');
		fileMenu.add(aboutMI);
		// separator
		fileMenu.addSeparator();
		// exit
		ImageIcon exitIcon = new ImageIcon(getClass().getResource("exit.png"));
		JMenuItem exitMI = new JMenuItem("Exit", exitIcon);
		exitMI.setMnemonic('x');
		exitMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				InputEvent.CTRL_MASK));
		fileMenu.add(exitMI);

		// action listeners
		aboutMI.addActionListener(aboutHdl);
		exitMI.addActionListener(exitHdl);

		// set layout of the main window
		mainContentPane = window.getContentPane();
		mainContentPane.setLayout(new BorderLayout());

		// main view
		FormView form = new FormView();
		form.addCredentialListener(credListener);
		mainContentPane.add(form, BorderLayout.NORTH);
		resultView = new DisplayResult();
		mainContentPane.add(resultView, BorderLayout.CENTER);
	}

	/**
	 * Ends the program by displaying the exception.
	 * 
	 * @param e
	 *            the thrown exception
	 * 
	 * @since 1.0
	 */
	private void exit(Exception e) {
		System.err.println(e.getMessage());
		System.exit(1);
	}

	/**
	 * Exits the program.
	 * 
	 * @since 1.0
	 */
	private void exit() {
		System.exit(0);
	}
}

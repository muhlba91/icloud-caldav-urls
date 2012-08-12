package com.niftyside.icloud.calendars.app;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import com.niftyside.icloud.calendars.api.ICloud;
import com.niftyside.icloud.calendars.api.exception.ICloudException;
import com.niftyside.icloud.calendars.api.model.UserData;
import com.niftyside.icloud.calendars.app.gui.event.CredentialEvent;
import com.niftyside.icloud.calendars.app.gui.event.UserDataEvent;
import com.niftyside.icloud.calendars.app.gui.listener.CredentialListener;
import com.niftyside.icloud.calendars.app.gui.listener.UserDataListener;
import com.niftyside.icloud.calendars.app.gui.view.AboutDialog;
import com.niftyside.icloud.calendars.app.gui.view.FormView;
import com.niftyside.icloud.calendars.app.gui.view.ProgressBarOverlay;
import com.niftyside.icloud.calendars.app.gui.view.ResultView;

/**
 * The query engine application for GUI access.
 * 
 * @author Daniel Muehlbachler
 * @copyright 2012 Daniel Muehlbachler
 * 
 * @see http://icloud.niftyside.com
 * 
 * @version 1.2
 * 
 */
public class GUI {
	/* * * * * Variables * * * * */

	/** The main application window. */
	private final JFrame window;
	/** The {@link UserDataListener}s. */
	private final List<UserDataListener> listeners;
	/** The progress bar. */
	private final ProgressBarOverlay progessBar;

	/* * * * * Static methods * * * * */

	/**
	 * Main application method.
	 * 
	 * @param args
	 *            command line arguments (not used)
	 * 
	 * @since 1.1
	 */
	public static void main(final String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// do nothing -> it doesn't matter
		}

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new GUI();
			}
		});
	}

	/* * * * * Constructor * * * * */

	/**
	 * Creates a new GUI.
	 * 
	 * @since 1.1
	 */
	private GUI() {
		listeners = new ArrayList<>();

		window = new JFrame(AppConstants.APPLICATION_NAME);
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		createMenu();
		initializeDefaultView();

		window.pack();
		window.setLocation(50, 50);

		progessBar = new ProgressBarOverlay(window);

		window.setVisible(true);
	}

	/* * * * * Private methods * * * * */

	/**
	 * Creates the menu.
	 * 
	 * @since 1.1
	 */
	private void createMenu() {
		final JMenuBar menuBar = new JMenuBar();
		window.setJMenuBar(menuBar);

		final JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		menuBar.add(file);
		file.add(new AbstractAction("About", new ImageIcon(getClass()
				.getResource("about.png"))) {
			private static final long serialVersionUID = 1L;

			{
				putValue(MNEMONIC_KEY, (int) 'a');
			}

			@Override
			public void actionPerformed(final ActionEvent e) {
				final AboutDialog about = new AboutDialog(window);
				about.setVisible(true);
			}
		});
		file.addSeparator();
		file.add(new AbstractAction("Exit", new ImageIcon(getClass()
				.getResource("exit.png"))) {
			private static final long serialVersionUID = 1L;

			{
				putValue(MNEMONIC_KEY, (int) 'x');
				putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Q,
						InputEvent.CTRL_MASK));
			}

			@Override
			public void actionPerformed(final ActionEvent e) {
				System.exit(0);
			}
		});
	}

	/**
	 * Initializes the default view.
	 * 
	 * @since 1.1
	 */
	private void initializeDefaultView() {
		window.setLayout(new BorderLayout());

		final FormView form = new FormView();
		form.addCredentialListener(new CredentialListener() {
			@Override
			public void credentialsSent(final CredentialEvent e) {
				fetchUserData(e.getAppleID(), e.getPassword(), e.getServer());
			}
		});
		window.add(form, BorderLayout.NORTH);

		final ResultView result = new ResultView();
		addUserDataListener(result.getUserDataListener());
		window.add(result, BorderLayout.CENTER);
	}

	/**
	 * Adds a new {@link UserDataListener}.
	 * 
	 * @param listener
	 *            the listener
	 * 
	 * @since 1.1
	 */
	private void addUserDataListener(final UserDataListener listener) {
		listeners.add(listener);
	}

	/**
	 * Removes a {@link UserDataListener}.
	 * 
	 * @param listener
	 *            the listener
	 * 
	 * @since 1.1
	 */
	@SuppressWarnings("unused")
	private void removeUserDataListener(final UserDataListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Fetches the {@link UserData} and fires the {@link UserDataEvent}.
	 * 
	 * @param appleID
	 *            the Apple ID
	 * @param password
	 *            the password
	 * @param server
	 *            the server
	 * 
	 * @since 1.1
	 */
	private void fetchUserData(final String appleID, final String password,
			final String server) {
		final Thread fetch = new Thread() {
			@Override
			public void run() {
				UserData userData = null;
				ICloudException exception = null;

				try {
					userData = ICloud.queryData(server, appleID, password);
				} catch (final ICloudException e) {
					exception = e;
				}

				fireUserDataEvent(userData, exception);
			}
		};

		progessBar.setVisible(true);

		fetch.start();
	}

	/**
	 * Fires a {@link UserDataEvent}.
	 * 
	 * @param userData
	 *            the user data
	 * @param exception
	 *            the exception; null if none was thrown
	 * 
	 * @since 1.1
	 */
	private void fireUserDataEvent(final UserData userData,
			final ICloudException exception) {
		progessBar.dispose();

		final UserDataEvent e = new UserDataEvent(userData, exception);
		for (final UserDataListener l : listeners) {
			l.newUserData(e);
		}
	}
}

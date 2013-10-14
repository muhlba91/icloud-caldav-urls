package com.niftyside.icloud.calendars.app.gui

import com.niftyside.icloud.calendars.api.Calendars
import com.niftyside.icloud.calendars.api.exception.CalendarsException
import com.niftyside.icloud.calendars.api.model.UserData
import com.niftyside.icloud.calendars.app.gui.event.CredentialEvent
import com.niftyside.icloud.calendars.app.gui.event.UserDataEvent
import com.niftyside.icloud.calendars.app.gui.listener.CredentialListener
import com.niftyside.icloud.calendars.app.gui.listener.UserDataListener
import com.niftyside.icloud.calendars.app.gui.view.About
import com.niftyside.icloud.calendars.app.gui.view.Form
import com.niftyside.icloud.calendars.app.gui.view.ProgressBarOverlay
import com.niftyside.icloud.calendars.app.gui.view.Results
import org.apache.commons.logging.LogFactory

import javax.swing.*
import java.awt.*
import java.awt.event.*
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.Executors

/**
 * The query engine application for GUI access.
 *
 * Project: iCloud calendars
 * User: daniel
 * Date: 29.09.13
 * Time: 19:22
 *
 * @author Daniel Muehlbachler
 * @copyright 2011-2013 Daniel Muehlbachler
 *
 * @see {@link http://icloud.niftyside.com}
 *
 * @version 2.0.0
 */
class Application {
	/* * * * * Variables * * * * */

	public static final def VERSION = "2.0.0"
	public static final def APPLICATION_NAME = "iCloud calendar URLs"
	public static final def COPYRIGHT_LINK = "<a href='http://www.niftyside.com'>" + Calendars.COPYRIGHT_NAME + "</a>"
	private static final def LOGGER = LogFactory.getLog(Application.class)
	private final def window
	private final def progressBar
	private final def userDataListeners
	private final def executorService
	private final def engine


	/* * * * * Static methods * * * * */

	/**
	 * Main application method.
	 *
	 * @param args
	 *            command line arguments (not used)
	 *
	 * @since 2.0.0
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
		} catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			LOGGER.info("Look and feel not set.", e)
		}

		SwingUtilities.invokeLater({
			new Application()
		} as Runnable)
	}

	/* * * * * Constructor * * * * */

	/**
	 * Creates the GUI.
	 *
	 * @since 2.0.0
	 */
	private Application() {
		userDataListeners = new CopyOnWriteArrayList<UserDataListener>()
		executorService = Executors.newSingleThreadExecutor()
		engine = new Calendars()

		window = new JFrame(APPLICATION_NAME)
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
		window.addWindowStateListener(new WindowAdapter() {
			@Override
			void windowClosing(WindowEvent e) {
				executorService.shutdownNow()
				super.windowClosing(e)
			}
		})

		createMenu()
		initializeDefaultView()

		window.pack()
		window.setLocation(50, 50)

		progressBar = new ProgressBarOverlay(window)

		window.setVisible(true)
	}

	/* * * * * Private methods * * * * */

	/**
	 * Creates the menu.
	 *
	 * @since 2.0.0
	 */
	private def createMenu() {
		def menuBar = new JMenuBar()
		window.setJMenuBar(menuBar)

		def file = new JMenu("File")
		file.setMnemonic(KeyEvent.VK_F)
		menuBar.add(file)
		file.add(new AbstractAction("About", new ImageIcon(getClass().getResource("about.png"))) {
			{
				putValue(MNEMONIC_KEY, (int) 'a')
			}

			@Override
			void actionPerformed(ActionEvent e) {
				def about = new About(window)
				about.setVisible(true)
			}
		})
		file.addSeparator()
		file.add(new AbstractAction("Exit", new ImageIcon(getClass().getResource("exit.png"))) {
			{
				putValue(MNEMONIC_KEY, (int) 'x')
				putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK))
			}

			@Override
			void actionPerformed(ActionEvent e) {
				window.dispose()
			}
		})
	}

	/**
	 * Initializes the default view.
	 *
	 * @since 2.0.0
	 */
	private def initializeDefaultView() {
		window.setLayout(new BorderLayout())

		def form = new Form()
		form.addCredentialListener({ CredentialEvent event ->
			fetchUserData(event.appleId, event.password, event.server)
		} as CredentialListener)
		window.add(form, BorderLayout.NORTH)

		def result = new Results()
		addUserDataListener(result.getUserDataListener())
		window.add(result, BorderLayout.CENTER)
	}

	/**
	 * Adds a new {@link UserDataListener}.
	 *
	 * @param listener
	 *            the listener
	 *
	 * @since 2.0.0
	 */
	private def addUserDataListener(UserDataListener listener) {
		userDataListeners.add(listener)
	}

	/**
	 * Removes a {@link UserDataListener}.
	 *
	 * @param listener
	 *            the listener
	 *
	 * @since 2.0.0
	 */
	@SuppressWarnings("unused")
	private def removeUserDataListener(UserDataListener listener) {
		userDataListeners.remove(listener)
	}

	/**
	 * Fetches the {@link UserData} and fires the {@link UserDataEvent}.
	 *
	 * @param appleId
	 *            the Apple ID
	 * @param password
	 *            the password
	 * @param server
	 *            the server
	 *
	 * @since 2.0.0
	 */
	private def fetchUserData(String appleId, String password, String server) {
		executorService.execute({
			progressBar.setVisible(true)

			engine.setLoginData(server, appleId, password)

			def userData = null
			def exception = null

			try {
				userData = engine.getUserData()
			} catch(CalendarsException e) {
				exception = e
			}

			progressBar.dispose()
			fireUserDataEvent(userData, exception)
		} as Runnable)
	}

	/**
	 * Fires an event to indicate newly available user data.
	 *
	 * @param userData
	 * 				the user data
	 * @param exception
	 * 				the thrown exception (if applicable)
	 *
	 * @since 2.0.0
	 */
	private def fireUserDataEvent(UserData userData, CalendarsException exception) {
		def event = new UserDataEvent(userData, exception)
		userDataListeners.each {
			it.newUserData(event)
		}
	}
}

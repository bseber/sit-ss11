package de.hsma.sit.ss11.frontend;

import de.hsma.sit.ss11.frontend.controller.RegisterDialogUIHandler;
import de.hsma.sit.ss11.frontend.controller.LoginUIHandler;
import de.hsma.sit.ss11.frontend.controller.MainWindowUIHandler;
import de.hsma.sit.ss11.frontend.resources.Resources;
import de.hsma.sit.ss11.frontend.view.RegisterDialog;
import de.hsma.sit.ss11.frontend.view.LoginDialog;
import de.hsma.sit.ss11.frontend.view.MainWindow;

public class ClientFactory {

	private static final ClientFactory INSTANCE = new ClientFactory();

	private Resources resources;
	private MainWindow mainWindow;

	private RegisterDialog.UIHandler registerDialogUIHandler;
	private LoginDialog.UIHandler loginUIHandler;
	private MainWindow.UIHandler mainWindowUIHandler;

	/**
	 * ClientFactory should only be accessed by {@link #getInstance()}
	 */
	private ClientFactory() {
	}

	public static ClientFactory getInstance() {
		return INSTANCE;
	}

	public Resources resources() {
		if (resources == null) {
			resources = new Resources();
		}
		return resources;
	}

	public MainWindow mainWindow() {
		return mainWindow(true);
	}

	public RegisterDialog.UIHandler registerDialogUIHandler() {
		if (registerDialogUIHandler == null) {
			registerDialogUIHandler = new RegisterDialogUIHandler();
		}
		return registerDialogUIHandler;
	}

	public MainWindow mainWindow(boolean successfulLogin) {
		if (mainWindow == null) {
			mainWindow = new MainWindow(resources(), mainWindowUIHandler());
		}
		return mainWindow;
	}

	public LoginDialog.UIHandler loginUIHandler() {
		if (loginUIHandler == null) {
			loginUIHandler = new LoginUIHandler();
		}
		return loginUIHandler;
	}

	public MainWindow.UIHandler mainWindowUIHandler() {
		if (mainWindowUIHandler == null) {
			mainWindowUIHandler = new MainWindowUIHandler();
		}
		return mainWindowUIHandler;
	}

}

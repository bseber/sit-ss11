package de.hsma.sit.ss11.frontend;

import de.hsma.sit.ss11.frontend.controller.AddUserDialogUIHandler;
import de.hsma.sit.ss11.frontend.controller.LoginUIHandler;
import de.hsma.sit.ss11.frontend.controller.MainWindowUIHandler;
import de.hsma.sit.ss11.frontend.resources.Resources;
import de.hsma.sit.ss11.frontend.view.AddUserDialog;
import de.hsma.sit.ss11.frontend.view.LoginDialog;
import de.hsma.sit.ss11.frontend.view.MainWindow;

public class ClientFactory {

	private static final ClientFactory INSTANCE = new ClientFactory();

	private Resources resources;
	private AddUserDialog.Delegate addUserDelegate;
	private MainWindow mainWindow;

	private LoginDialog.Delegate loginDelegate;
	private MainWindow.Delegate mainWindowDelegate;

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

	public AddUserDialog.Delegate addUserDelegate() {
		if (addUserDelegate == null) {
			addUserDelegate = new AddUserDialogUIHandler();
		}
		return addUserDelegate;
	}

	public MainWindow mainWindow(boolean successfulLogin) {
		if (mainWindow == null) {
			mainWindow = new MainWindow(resources(), mainWindowDelegate());
		}
		return mainWindow;
	}

	public LoginDialog.Delegate loginDelegate() {
		if (loginDelegate == null) {
			loginDelegate = new LoginUIHandler();
		}
		return loginDelegate;
	}

	public MainWindow.Delegate mainWindowDelegate() {
		if (mainWindowDelegate == null) {
			mainWindowDelegate = new MainWindowUIHandler(this);
		}
		return mainWindowDelegate;
	}

}

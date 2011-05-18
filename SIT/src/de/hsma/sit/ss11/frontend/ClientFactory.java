package de.hsma.sit.ss11.frontend;

import de.hsma.sit.ss11.frontend.controller.LoginController;
import de.hsma.sit.ss11.frontend.controller.MainWindowController;
import de.hsma.sit.ss11.frontend.view.LoginDialog;
import de.hsma.sit.ss11.frontend.view.MainWindow;
import de.hsma.sit.ss11.frontend.view.resources.Resources;

public class ClientFactory {
	
	private static final ClientFactory INSTANCE = new ClientFactory();
	
	private Resources resources;
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
	
	public LoginDialog.Delegate loginDelegate() {
		if (loginDelegate == null) {
			loginDelegate = new LoginController();
		}
		return loginDelegate;
	}
	
	public MainWindow.Delegate mainWindowDelegate() {
		if (mainWindowDelegate == null) {
			mainWindowDelegate = new MainWindowController();
		}
		return mainWindowDelegate;
	}

}

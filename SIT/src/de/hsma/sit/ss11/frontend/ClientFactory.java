package de.hsma.sit.ss11.frontend;

import de.hsma.sit.ss11.frontend.controller.LoginController.LoginView;
import de.hsma.sit.ss11.frontend.controller.LoginUIHandler;
import de.hsma.sit.ss11.frontend.controller.MainWindowUIHandler;
import de.hsma.sit.ss11.frontend.controller.RegisterController.RegisterView;
import de.hsma.sit.ss11.frontend.controller.RegisterDialogUIHandler;
import de.hsma.sit.ss11.frontend.view.LoginDialog;
import de.hsma.sit.ss11.frontend.view.MainWindow;
import de.hsma.sit.ss11.frontend.view.RegisterDialog;

public class ClientFactory {

	private static final ClientFactory INSTANCE = new ClientFactory();

	private LoginView loginView;
	private MainWindow mainWindow;
	private RegisterView registerView;

	private RegisterDialog.MyUIHandler registerDialogUIHandler;
	private LoginDialog.MyUIHandler loginUIHandler;
	private MainWindow.MyUIHandler mainWindowUIHandler;

	/**
	 * ClientFactory should only be accessed by {@link #getInstance()}
	 */
	private ClientFactory() {
	}

	public static ClientFactory getInstance() {
		return INSTANCE;
	}

	public MainWindow mainWindow() {
		return mainWindow(true);
	}

	public RegisterDialog.MyUIHandler registerDialogUIHandler() {
		if (registerDialogUIHandler == null) {
			registerDialogUIHandler = new RegisterDialogUIHandler();
		}
		return registerDialogUIHandler;
	}

	public MainWindow mainWindow(boolean successfulLogin) {
		if (mainWindow == null) {
			mainWindow = new MainWindow(mainWindowUIHandler());
		}
		return mainWindow;
	}

	public LoginDialog.MyUIHandler loginUIHandler() {
		if (loginUIHandler == null) {
			loginUIHandler = new LoginUIHandler();
		}
		return loginUIHandler;
	}
	
	public LoginView loginView() {
		if (loginView == null) {
			loginView = new LoginDialog(loginUIHandler());
		}
		return loginView;
	}

	public MainWindow.MyUIHandler mainWindowUIHandler() {
		if (mainWindowUIHandler == null) {
			mainWindowUIHandler = new MainWindowUIHandler();
		}
		return mainWindowUIHandler;
	}
	
	public RegisterView registerView() {
		if (registerView == null) {
			registerView = new RegisterDialog(registerDialogUIHandler());
		}
		return registerView;
	}

}

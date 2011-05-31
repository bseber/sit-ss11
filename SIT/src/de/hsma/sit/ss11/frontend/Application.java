package de.hsma.sit.ss11.frontend;

import de.hsma.sit.ss11.entities.AnyUser;
import de.hsma.sit.ss11.frontend.controller.LoginController;
import de.hsma.sit.ss11.frontend.controller.MainWindowController;
import de.hsma.sit.ss11.frontend.controller.RegisterController;

public class Application {

	private static ClientFactory clientFactory;
	private static LoginController loginController;
	private static MainWindowController mainWindowController;
	private static RegisterController registerController;
	
	private static AnyUser currentUser;

	public static void main(String[] args) {
		init();
		loginController.showView();
	}
	
	public static void showMainWindowAndHideLoginDialog() {
		loginController.hideView();
		mainWindowController.showView();
	}
	
	public static void showRegisterDialog() {
		registerController.showView();
	}
	
	private static void init() {
		clientFactory = ClientFactory.getInstance();
		loginController = new LoginController(clientFactory.loginView(), clientFactory.loginUIHandler());
		mainWindowController = new MainWindowController(clientFactory.mainWindow(), clientFactory.mainWindowUIHandler());
		registerController = new RegisterController(clientFactory.registerView(), clientFactory.registerDialogUIHandler());
	}
	
	public static AnyUser getCurrentUser() {
		return currentUser;
	}
	
	public static void setCurrentUser(AnyUser currentUser) {
		Application.currentUser = currentUser;
	}

}

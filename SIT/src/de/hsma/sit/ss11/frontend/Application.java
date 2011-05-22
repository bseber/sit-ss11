package de.hsma.sit.ss11.frontend;

import de.hsma.sit.ss11.frontend.controller.LoginController;
import de.hsma.sit.ss11.frontend.view.MainWindow;

public class Application {

	private static ClientFactory clientFactory;
	private static LoginController loginController;
	
	public static void main(String[] args) {
		clientFactory = ClientFactory.getInstance();
		loginController = new LoginController();
		loginController.setDialogVisible(true);
	}
	
	public static void showMainWindow() {
		loginController.setDialogVisible(false);
		MainWindow window = new MainWindow(clientFactory.resources(), clientFactory.mainWindowUIHandler());
		window.setVisible(true);
	}

}

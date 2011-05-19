package de.hsma.sit.ss11.frontend;

import de.hsma.sit.ss11.frontend.view.LoginDialog;
import de.hsma.sit.ss11.frontend.view.MainWindow;

public class Application {

	private static ClientFactory clientFactory;
	private static LoginDialog dialog;
	
	public static void main(String[] args) {
		clientFactory = ClientFactory.getInstance();
		run();
	}
	
	public static void run() {
		dialog = new LoginDialog(clientFactory.resources(), clientFactory.loginDelegate());
		dialog.setVisible(true);
	}
	
	public static void showMainWindow(boolean successfulLogin) {
		dialog.dispose();
		MainWindow window = new MainWindow(clientFactory.resources(), clientFactory.mainWindowDelegate(), successfulLogin);
		window.setVisible(true);
	}

}

package de.hsma.sit.ss11.frontend.controller;

import de.hsma.sit.ss11.frontend.view.LoginDialog;

public class LoginController {

	public interface LoginView {
		void setVisible(boolean b);

		void dispose();
	}

	private LoginView view;

	public LoginController() {
		view = new LoginDialog(new LoginUIHandler());
	}

	public void setDialogVisible(boolean b) {
		if (b) {
			view.setVisible(b);
		} else {
			view.dispose();
		}
	}

}

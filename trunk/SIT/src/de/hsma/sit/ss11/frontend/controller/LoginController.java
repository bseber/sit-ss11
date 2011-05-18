package de.hsma.sit.ss11.frontend.controller;

import de.hsma.sit.ss11.frontend.Application;
import de.hsma.sit.ss11.frontend.view.LoginDialog;

public class LoginController implements LoginDialog.Delegate {

	@Override
	public void loginClicked(String username, char[] password) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancelClicked() {
		Application.showMainWindow(false);
	}

}

package de.hsma.sit.ss11.frontend.controller;

import javax.swing.JDialog;

import de.hsma.sit.ss11.entities.AnyUser;
import de.hsma.sit.ss11.factories.Factory;
import de.hsma.sit.ss11.frontend.Application;
import de.hsma.sit.ss11.frontend.resources.Resources;
import de.hsma.sit.ss11.frontend.scaffold.View;
import de.hsma.sit.ss11.frontend.view.ErrorDialog;
import de.hsma.sit.ss11.frontend.view.LoginDialog;
import de.hsma.sit.ss11.services.SecurityService;

public class LoginUIHandler implements LoginDialog.MyUIHandler {

	private final SecurityService securityService;

	public LoginUIHandler() {
		securityService = Factory.getInstance().getSecurityService();
	}

	@Override
	public void onCancelClicked() {
		System.exit(0);
	}

	@Override
	public void onLoginClicked(JDialog parent, String username, char[] password) {
		String pass = String.valueOf(password);
		AnyUser user = securityService.login(username, pass);
		pass = null; // security reason
		if (user != null) {
			Application.setCurrentUser(user);
			Application.showMainWindowAndHideLoginDialog();
		} else {
			Resources resources = Resources.getInstance();
			new ErrorDialog(parent, "", resources.messages().loginError())
					.setVisible(true);
		}
	}

	@Override
	public void onRegisterClicked() {
		Application.showRegisterDialog();
	}

	@Override
	public void setView(View view) {
		// TODO Auto-generated method stub
		
	}

}
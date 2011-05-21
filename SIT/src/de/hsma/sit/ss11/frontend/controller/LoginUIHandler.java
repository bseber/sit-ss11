package de.hsma.sit.ss11.frontend.controller;

import javax.swing.JDialog;

import de.hsma.sit.ss11.entities.AnyUser;
import de.hsma.sit.ss11.factories.Factory;
import de.hsma.sit.ss11.frontend.Application;
import de.hsma.sit.ss11.frontend.resources.Resources;
import de.hsma.sit.ss11.frontend.view.ErrorDialog;
import de.hsma.sit.ss11.frontend.view.LoginDialog;
import de.hsma.sit.ss11.services.SecurityService;

public class LoginUIHandler implements LoginDialog.Delegate {

	private final SecurityService securityService;

	public LoginUIHandler() {
		securityService = Factory.getInstance().getSecurityService();
	}

	@Override
	public void loginClicked(JDialog parent, String username, char[] password) {
//		String pass = password.toString();
//		AnyUser user = securityService.login(username, pass);
//		pass = null;
//		if (user != null) {
			Application.showMainWindow();
//		} else {
//			Resources resources = Resources.getInstance();
//			new ErrorDialog(parent, resources.messages().loginError(),
//					resources.messages().userNotFound(), resources)
//					.setVisible(true);
//		}
	}

	@Override
	public void cancelClicked() {
		System.exit(0);
	}

}

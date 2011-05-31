package de.hsma.sit.ss11.frontend.controller;

import de.hsma.sit.ss11.frontend.scaffold.AbstractController;
import de.hsma.sit.ss11.frontend.scaffold.View;
import de.hsma.sit.ss11.frontend.view.LoginDialog;

public class LoginController extends
		AbstractController<LoginController.LoginView, LoginDialog.MyUIHandler> {

	public interface LoginView extends View {
	}

	public LoginController(LoginController.LoginView view,
			LoginDialog.MyUIHandler uiHandler) {
		super(view, uiHandler);
	}

}

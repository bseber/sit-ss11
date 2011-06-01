package de.hsma.sit.ss11.frontend.controller;

import de.hsma.sit.ss11.factories.Factory;
import de.hsma.sit.ss11.frontend.view.RegisterDialog;
import de.hsma.sit.ss11.services.UserService;

public class RegisterDialogUIHandler implements RegisterDialog.MyUIHandler {

	private final UserService userService = Factory.getInstance()
			.getUserService();

	@Override
	public boolean onRegisterClicked(String username, String password,
			String privatePassword) {
		return userService.registerUser(username, password, privatePassword);
	}

	@Override
	public void setView(RegisterDialog view) {
	}

}

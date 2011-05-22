package de.hsma.sit.ss11.frontend.controller;

import de.hsma.sit.ss11.factories.Factory;
import de.hsma.sit.ss11.frontend.view.AddUserDialog;
import de.hsma.sit.ss11.services.UserService;

public class AddUserDialogUIHandler implements AddUserDialog.UIHandler {

	private final UserService userService = Factory.getInstance()
			.getUserService();

	@Override
	public boolean addUser(String username, String password,
			String privatePassword) {
		return userService.registerUser(username, password, privatePassword);
	}

}

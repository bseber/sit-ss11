package de.hsma.sit.ss11.frontend.controller;

import de.hsma.sit.ss11.factories.Factory;
import de.hsma.sit.ss11.frontend.view.AddUserDialog;
import de.hsma.sit.ss11.services.UserService;

public class AddUserDialogUIHandler implements AddUserDialog.Delegate {

	UserService userService = Factory.getInstance().getUserService();
	
	@Override
	public void addUser(String username, String password) {
		// TODO Auto-generated method stub
		
	}

}

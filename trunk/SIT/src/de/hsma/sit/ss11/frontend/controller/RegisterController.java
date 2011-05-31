package de.hsma.sit.ss11.frontend.controller;

import de.hsma.sit.ss11.frontend.scaffold.AbstractController;
import de.hsma.sit.ss11.frontend.scaffold.View;
import de.hsma.sit.ss11.frontend.view.RegisterDialog;

public class RegisterController
		extends
		AbstractController<RegisterController.RegisterView, RegisterDialog.MyUIHandler> {

	public interface RegisterView extends View {

	}

	public RegisterController(RegisterView view,
			RegisterDialog.MyUIHandler uiHandler) {
		super(view, uiHandler);
	}

}

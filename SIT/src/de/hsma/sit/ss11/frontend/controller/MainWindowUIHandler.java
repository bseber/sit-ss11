package de.hsma.sit.ss11.frontend.controller;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import de.hsma.sit.ss11.entities.FileInfo;
import de.hsma.sit.ss11.frontend.ClientFactory;
import de.hsma.sit.ss11.frontend.view.AddUserDialog;
import de.hsma.sit.ss11.frontend.view.MainWindow;

public class MainWindowUIHandler implements MainWindow.Delegate {

	private final ClientFactory clientFactory;
	private AddUserDialog addUserDialog;

	public MainWindowUIHandler(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public void about(JFrame parent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addUser(JFrame parent) {
		if (addUserDialog == null) {
			addUserDialog = new AddUserDialog(parent,
					clientFactory.resources(), clientFactory.addUserDelegate());
		}
		addUserDialog.setVisible(true);
	}

	@Override
	public boolean addNewFile() {
		// TODO Auto-generated method stub
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.showOpenDialog(null);
		return false;
	}

	@Override
	public void clickedOnFile(FileInfo file) {
		// TODO Auto-generated method stub

	}

	@Override
	public void downloadFile() {
		// TODO Auto-generated method stub

	}

	@Override
	public void logoutAndExit() {
		// TODO Auto-generated method stub
		System.exit(0);
	}

	@Override
	public boolean removeFile() {
		// TODO Auto-generated method stub
		return false;
	}

}

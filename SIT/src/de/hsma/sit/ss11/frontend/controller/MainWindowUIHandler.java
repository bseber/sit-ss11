package de.hsma.sit.ss11.frontend.controller;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import de.hsma.sit.ss11.entities.FileInfo;
import de.hsma.sit.ss11.frontend.ClientFactory;
import de.hsma.sit.ss11.frontend.view.AddUserDialog;
import de.hsma.sit.ss11.frontend.view.InfoDialog;
import de.hsma.sit.ss11.frontend.view.MainWindow;

public class MainWindowUIHandler implements MainWindow.UIHandler {

	private final ClientFactory clientFactory;
	private AddUserDialog addUserDialog;
	private InfoDialog infoDialog;

	public MainWindowUIHandler(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public void refreshUserList() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onAddFileClicked() {
		// TODO Auto-generated method stub
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.showOpenDialog(null);
		return false;
	}

	@Override
	public void onAddUserClicked(MainWindow mainWindow, JFrame parent) {
		if (addUserDialog == null) {
			addUserDialog = new AddUserDialog(mainWindow, parent,
					clientFactory.resources(), clientFactory.addUserUIHandler());
		}
		addUserDialog.setVisible(true);
	}

	@Override
	public void onDownloadFileClicked() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFileSelected(FileInfo file) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onInfoClicked(JFrame parent) {
		if (infoDialog == null) {
			infoDialog = new InfoDialog(parent);
		}
		infoDialog.setVisible(true);
	}

	@Override
	public void onLogoutClicked() {
		// TODO logout?
		System.exit(0);
	}

	@Override
	public boolean onRemoveFileClicked() {
		// TODO Auto-generated method stub
		return false;
	}

}

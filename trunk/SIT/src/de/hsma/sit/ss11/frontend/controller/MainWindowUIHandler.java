package de.hsma.sit.ss11.frontend.controller;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import de.hsma.sit.ss11.entities.FileInfo;
import de.hsma.sit.ss11.frontend.view.InfoDialog;
import de.hsma.sit.ss11.frontend.view.MainWindow;

public class MainWindowUIHandler implements MainWindow.UIHandler {

	private InfoDialog infoDialog;

	public MainWindowUIHandler() {
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

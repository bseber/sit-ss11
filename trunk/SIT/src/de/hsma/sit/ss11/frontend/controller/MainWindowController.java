package de.hsma.sit.ss11.frontend.controller;

import javax.swing.JFileChooser;

import de.hsma.sit.ss11.frontend.view.MainWindow;

public class MainWindowController implements MainWindow.Delegate {

	@Override
	public boolean addNewFile() {
		// TODO Auto-generated method stub
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.showOpenDialog(null);
		
		return false;
	}

	@Override
	public void downloadFile() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean removeFile() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void logoutAndExit() {
		// TODO Auto-generated method stub
		System.exit(0);
	}

}

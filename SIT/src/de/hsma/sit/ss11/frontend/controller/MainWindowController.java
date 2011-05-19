package de.hsma.sit.ss11.frontend.controller;

import javax.swing.JFileChooser;

import de.hsma.sit.ss11.frontend.view.MainWindow;

public class MainWindowController implements MainWindow.Delegate {

	@Override
	public void addFile() {
		// TODO Auto-generated method stub
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.showOpenDialog(null);
		
	}

	@Override
	public void downloadFile() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeFile() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void logoutAndExit() {
		// TODO Auto-generated method stub
		System.exit(0);
	}

}

package de.hsma.sit.ss11.frontend.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import de.hsma.sit.ss11.entities.AnyUser;
import de.hsma.sit.ss11.entities.FileInfo;
import de.hsma.sit.ss11.factories.Factory;
import de.hsma.sit.ss11.frontend.Application;
import de.hsma.sit.ss11.frontend.controller.MainWindowController.MainWindowView;
import de.hsma.sit.ss11.frontend.view.InfoDialog;
import de.hsma.sit.ss11.frontend.view.MainWindow;
import de.hsma.sit.ss11.frontend.view.widgets.PasswordDialog;
import de.hsma.sit.ss11.services.FileInfoService;
import de.hsma.sit.ss11.services.KeyBoxService;
import de.hsma.sit.ss11.services.UserService;

public class MainWindowUIHandler implements MainWindow.MyUIHandler {

	private final KeyBoxService keyBoxService;
	private UserService userService;

	private InfoDialog infoDialog;
	private FileInfoService fileInfoService;
	private MainWindowView view;

	public MainWindowUIHandler() {
		keyBoxService = Factory.getInstance().getKeyBoxService();
		fileInfoService = Factory.getInstance().getFileInfoService();
		userService = Factory.getInstance().getUserService();
	}

	@Override
	public void onAddFileClicked(MainWindow mainWindow) {
		// TODO Auto-generated method stub
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		// this method does not return till dialog is closed
		fileChooser.showOpenDialog(null);

		File file = fileChooser.getSelectedFile();
		if (file != null) {
			try {
				FileInfo savedFile = fileInfoService.saveFile(
						Application.getCurrentUser(), file);
				// add the file to the view
				view.addFileToList(savedFile);
				view.setInfoText("Datei wurde erfolgreich verschlüsselt und gespeichert.");
			} catch (Exception e) {
				e.printStackTrace();
				view.setInfoText("Datei konnte nicht hinzugefügt werden.");
			}
		} else {
			view.setInfoText("Datei konnte nicht hinzugefügt werden.");
		}
	}

	@Override
	public void onDownloadFileClicked(FileInfo file, String password) {
		AnyUser user = Application.getCurrentUser();
		File decryptedFile = fileInfoService.getFile(user, file, password);
		
		if (decryptedFile != null) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setSelectedFile(decryptedFile);
			fileChooser.showSaveDialog(null);
			view.setInfoText("Datei wurde entschlüsselt.");
		} else {
			view.setInfoText("Datei konnte nicht entschlüsselt wreden (Falsches Passwort?).");
		}
	}

	@Override
	public void onFileSelected(FileInfo file) {
		List<AnyUser> assigned = fileInfoService.getUsersWithKeyCopy(file);
		List<AnyUser> allUsersTmp = userService.getAllUser();
		AnyUser currentUser = Application.getCurrentUser();
		allUsersTmp.remove(currentUser);
		List<AnyUser> allUsers = new ArrayList<AnyUser>();
		// remove users who are assigned
		for (AnyUser u : allUsersTmp) {
			if (!assigned.contains(u)) {
				allUsers.add(u);
			}
		}
		view.setAssignedUsers(assigned);
		view.setUserList(allUsers);
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
		System.exit(0);
	}

	@Override
	public boolean onRemoveFileClicked(FileInfo file) {
		// TODO Auto-generated method stub
		fileInfoService.deleteFile(file);
		return false;
	}

	@Override
	public boolean onSaveClicked(FileInfo file, List<AnyUser> notAssigned,
			List<AnyUser> assigned, String password) {
		// not assigned users
		boolean b1 = true;
		for (AnyUser u : notAssigned) {
			b1 = keyBoxService.removeKeyCopyFromUser(u, file);
		}

		// assign users
		boolean b2 = false;
		if (b1) {
			AnyUser currentUser = Application.getCurrentUser();
			for (AnyUser u : assigned) {
				b2 = keyBoxService.giveKeyCopyToAnyUser(currentUser, u,
						file, password);
			}
		}

		return b1 && b2;
	}

	@Override
	public void setView(MainWindow view) {
		this.view = view;
	}

}

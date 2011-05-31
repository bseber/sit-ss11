package de.hsma.sit.ss11.frontend.controller;

import java.util.List;

import de.hsma.sit.ss11.entities.AnyUser;
import de.hsma.sit.ss11.entities.FileInfo;
import de.hsma.sit.ss11.factories.Factory;
import de.hsma.sit.ss11.frontend.Application;
import de.hsma.sit.ss11.frontend.scaffold.AbstractController;
import de.hsma.sit.ss11.frontend.scaffold.View;
import de.hsma.sit.ss11.frontend.view.MainWindow;
import de.hsma.sit.ss11.services.FileInfoService;
import de.hsma.sit.ss11.services.KeyBoxService;
import de.hsma.sit.ss11.services.SecurityService;
import de.hsma.sit.ss11.services.UserService;

public class MainWindowController
		extends
		AbstractController<MainWindowController.MainWindowView, MainWindow.MyUIHandler> {

	private UserService userService;
	private FileInfoService fileInfoService;
	private KeyBoxService keyBoxService;
	private SecurityService securityService;

	public interface MainWindowView extends View {
		
		void addFileToList(FileInfo file);
		
		/**
		 * @param fileList
		 *            the files to be displayed in the view
		 */
		void setFileList(List<FileInfo> fileList);

		/**
		 * @param file
		 * @param assignedUsers
		 */
		void setAssignedUsers(List<AnyUser> assignedUsers);

		void setInfoText(String text);

		/**
		 * @param userList
		 */
		void setUserList(List<AnyUser> userList);
	}

	// TODO use interface MainWindowView instead of the class MainWindow...
	public MainWindowController(MainWindow view,
			MainWindow.MyUIHandler uiHandler) {
		super(view, uiHandler);
		uiHandler.setView(view);
		userService = Factory.getInstance().getUserService();
		fileInfoService = Factory.getInstance().getFileInfoService();
		keyBoxService = Factory.getInstance().getKeyBoxService();
		securityService = Factory.getInstance().getSecurityService();
	}

	@Override
	protected void onShow() {
		refreshFiles();
		refreshUsers();
	}
	
	private void refreshFiles() {
		AnyUser user = Application.getCurrentUser();
		List<FileInfo> files = fileInfoService.getAllFileInfosOfUser(user);
		view.setFileList(files);
	}
	
	private void refreshUsers() {
		List<AnyUser> userList = userService.getAllUser();
		AnyUser currentUser = Application.getCurrentUser();
		userList.remove(currentUser);
		view.setUserList(userList);
	}

}

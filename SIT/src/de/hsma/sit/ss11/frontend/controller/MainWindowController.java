package de.hsma.sit.ss11.frontend.controller;

import java.util.List;

import de.hsma.sit.ss11.entities.AnyUser;
import de.hsma.sit.ss11.entities.FileInfo;

public class MainWindowController {

	public interface MainWindowView {
		/**
		 * @param fileList
		 *            the files to be displayed in the view
		 */
		void setFileList(List<FileInfo> fileList);

		/**
		 * @param file
		 * @param assignedUsers
		 */
		void setFileAssignedUsers(FileInfo file, List<AnyUser> assignedUsers);
		
		void setInfoText(String text);

		/**
		 * @param userList
		 */
		void setUserList(List<AnyUser> userList);
	}

	private MainWindowView view;

	public MainWindowController(MainWindowView view) {
		this.view = view;
	}

}

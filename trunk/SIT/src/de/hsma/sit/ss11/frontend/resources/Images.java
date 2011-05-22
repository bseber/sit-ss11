package de.hsma.sit.ss11.frontend.resources;

import java.net.URL;

import javax.swing.ImageIcon;

public class Images {
	
	private final String PATH;
	private final String ADD_USER_ICN = "user-add-icon.png";
	private final String ADD_USER_ICN_GLOW = "user-add-icon-glow.png";
	private final String ARROW_FORWARD = "forward-icon.png";
	private final String ARROW_BACK = "back-icon.png";
	private final String FILE_ADD_ICN = "file-add-icon.png";
	private final String FILE_ADD_ICN_GLOW = "file-add-icon-glow.png";
	private final String FILE_REM_ICN = "file-remove-icon.png";
	private final String FILE_REM_ICN_GLOW = "file-remove-icon-glow.png";
	private final String FILE_DOWN_ICN = "file-down-icon.png";
	private final String FILE_DOWN_ICN_GLOW = "file-down-icon-glow.png";
	private final String INFO_ICN = "info-icon.png";
	private final String INFO_ICN_BIG = "info-icon-48.png";
	private final String INFO_ICN_GLOW = "info-icon-glow.png";
	private final String INPUT_ERROR = "inputError-icon.png";
	private final String LOGOUT_ICN = "logout-icon.png";
	private final String LOGOUT_ICN_GLOW = "logout-icon-glow.png";
	private final String NAV_SEPARATOR = "nav-separator.png";
	private final String REMOVE_USER_ICN = "user-remove-icon.png";
	private final String REMOVE_USER_ICN_GLOW = "user-remove-icon-glow.png";
	private final String SAVE_ICN = "floppy-icon.png";
	private final String SECURITY_ICN = "security_icn.png";
	private final String USER_ICN = "user-icon.png";
	private final String WARNING_ICN = "warning-icon.png";
	
	/**
	 * Package-private because only {@link Resources} should instantiate this
	 * class.
	 */
	Images() {
		String packageName = this.getClass().getPackage().getName();
		packageName = packageName.replace('.', '/');
		PATH = packageName + "/";
	}
	
	public ImageIcon addFile() {
		return new ImageIcon(getResource(FILE_ADD_ICN));
	}
	
	/**
	 * @return mouse over icon to add a file
	 */
	public ImageIcon addFileGlow() {
		return new ImageIcon(getResource(FILE_ADD_ICN_GLOW));
	}
	
	public ImageIcon addUser() {
		return new ImageIcon(getResource(ADD_USER_ICN));
	}
	
	/**
	 * @return mouse over icon to add a new user
	 */
	public ImageIcon addUserGlow() {
		return new ImageIcon(getResource(ADD_USER_ICN_GLOW));
	}
	
	public ImageIcon arrowForward() {
		return new ImageIcon(getResource(ARROW_FORWARD));
	}
	
	public ImageIcon arrowBack() {
		return new ImageIcon(getResource(ARROW_BACK));
	}

	public ImageIcon downloadFile() {
		return new ImageIcon(getResource(FILE_DOWN_ICN));
	}
	
	/**
	 * @return mouse over icon to add a file
	 */
	public ImageIcon downloadFileGlow() {
		return new ImageIcon(getResource(FILE_DOWN_ICN_GLOW));
	}
	
	public ImageIcon info() {
		return new ImageIcon(getResource(INFO_ICN));
	}
	
	/**
	 * @return info icon 48*48
	 */
	public ImageIcon infoBig() {
		return new ImageIcon(getResource(INFO_ICN_BIG));
	}
	
	/**
	 * @return mouse over icon to get information about something
	 */
	public ImageIcon infoGlow() {
		return new ImageIcon(getResource(INFO_ICN_GLOW));
	}
	
	public ImageIcon inputError() {
		return new ImageIcon(getResource(INPUT_ERROR));
	}
	
	/**
	 * @return a lock icon
	 */
	public ImageIcon lock() {
		return new ImageIcon(getResource(SECURITY_ICN));
	}
	
	public ImageIcon logout() {
		return new ImageIcon(getResource(LOGOUT_ICN));
	}
	
	/**
	 * @return mouse over icon to remove a file
	 */
	public ImageIcon logoutGlow() {
		return new ImageIcon(getResource(LOGOUT_ICN_GLOW));
	}
	
	public ImageIcon navSeparator() {
		return new ImageIcon(getResource(NAV_SEPARATOR));
	}
	
	public ImageIcon removeFile() {
		return new ImageIcon(getResource(FILE_REM_ICN));
	}
	
	/**
	 * @return mouse over icon to remove a file
	 */
	public ImageIcon removeFileGlow() {
		return new ImageIcon(getResource(FILE_REM_ICN_GLOW));
	}
	
	public ImageIcon removeUser() {
		return new ImageIcon(getResource(REMOVE_USER_ICN));
	}
	
	/**
	 * @return mouse over icon to remove a user
	 */
	public ImageIcon removeUserGlow() {
		return new ImageIcon(getResource(REMOVE_USER_ICN_GLOW));
	}
	
	public ImageIcon save() {
		return new ImageIcon(getResource(SAVE_ICN));
	}
	
	public ImageIcon user() {
		return new ImageIcon(getResource(USER_ICN));
	}
	
	public ImageIcon warning() {
		return new ImageIcon(getResource(WARNING_ICN));
	}
	

	private URL getResource(String name) {
		return this.getClass().getClassLoader().getResource(PATH + name);
	}

}

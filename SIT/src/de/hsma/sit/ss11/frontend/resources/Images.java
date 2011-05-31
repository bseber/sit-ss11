package de.hsma.sit.ss11.frontend.resources;

import java.net.URL;

import javax.swing.ImageIcon;

public class Images {
	
	private final String PATH;
	
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
		return new ImageIcon(getResource("file-add-icon.png"));
	}
	
	/**
	 * @return mouse over icon to add a file
	 */
	public ImageIcon addFileGlow() {
		return new ImageIcon(getResource("file-add-icon-glow.png"));
	}
	
	public ImageIcon addUser() {
		return new ImageIcon(getResource("user-add-icon.png"));
	}
	
	/**
	 * @return mouse over icon to add a new user
	 */
	public ImageIcon addUserGlow() {
		return new ImageIcon(getResource("user-add-icon-glow.png"));
	}
	
	public ImageIcon arrowForward() {
		return new ImageIcon(getResource("forward-icon.png"));
	}
	
	public ImageIcon arrowBack() {
		return new ImageIcon(getResource("back-icon.png"));
	}

	public ImageIcon downloadFile() {
		return new ImageIcon(getResource("file-down-icon.png"));
	}
	
	/**
	 * @return mouse over icon to add a file
	 */
	public ImageIcon downloadFileGlow() {
		return new ImageIcon(getResource("file-down-icon-glow.png"));
	}
	
	public ImageIcon fileEditable() {
		return new ImageIcon(getResource("edit-icon.png"));
	}
	
	public ImageIcon fileLock() {
		return new ImageIcon(getResource("database-lock-icon-48.png"));
	}
	
	public ImageIcon fileReadable() {
		return new ImageIcon(getResource("read-icon.png"));
	}
	
	public ImageIcon info() {
		return new ImageIcon(getResource("info-icon.png"));
	}
	
	/**
	 * @return info icon 48*48
	 */
	public ImageIcon infoBig() {
		return new ImageIcon(getResource("info-icon-48.png"));
	}
	
	/**
	 * @return mouse over icon to get information about something
	 */
	public ImageIcon infoGlow() {
		return new ImageIcon(getResource("info-icon-glow.png"));
	}
	
	public ImageIcon inputError() {
		return new ImageIcon(getResource("inputError-icon.png"));
	}
	
	/**
	 * @return a lock icon
	 */
	public ImageIcon lock() {
		return new ImageIcon(getResource("security_icn.png"));
	}
	
	public ImageIcon logout() {
		return new ImageIcon(getResource("logout-icon.png"));
	}
	
	/**
	 * @return mouse over icon to remove a file
	 */
	public ImageIcon logoutGlow() {
		return new ImageIcon(getResource("logout-icon-glow.png"));
	}
	
	public ImageIcon removeFile() {
		return new ImageIcon(getResource("file-remove-icon.png"));
	}
	
	/**
	 * @return mouse over icon to remove a file
	 */
	public ImageIcon removeFileGlow() {
		return new ImageIcon(getResource("file-remove-icon-glow.png"));
	}
	
	public ImageIcon save() {
		return new ImageIcon(getResource("floppy-icon.png"));
	}
	
	public ImageIcon user() {
		return new ImageIcon(getResource("user-icon.png"));
	}
	
	/**
	 * @return a small (16*16) user icon
	 */
	public ImageIcon userSmall() {
		return new ImageIcon(getResource("user-icon-16.png"));
	}
	
	public ImageIcon warning() {
		return new ImageIcon(getResource("warning-icon.png"));
	}
	

	private URL getResource(String name) {
		return this.getClass().getClassLoader().getResource(PATH + name);
	}

}

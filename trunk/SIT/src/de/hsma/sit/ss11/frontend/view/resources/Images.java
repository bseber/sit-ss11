package de.hsma.sit.ss11.frontend.view.resources;

import java.net.URL;

import javax.swing.ImageIcon;

public class Images {
	
	private final String PATH;
	private final String ARROW_FORWARD = "forward-icon.png";
	private final String ARROW_BACK = "back-icon.png";
	private final String FILE_ADD_ICN = "file-add-icon.png";
	private final String FILE_ADD_ICN_GLOW = "file-add-icon-glow.png";
	private final String FILE_REM_ICN = "file-remove-icon.png";
	private final String FILE_REM_ICN_GLOW = "file-remove-icon-glow.png";
	private final String FILE_DOWN_ICN = "file-down-icon.png";
	private final String FILE_DOWN_ICN_GLOW = "file-down-icon-glow.png";
	private final String LOGOUT_ICN = "logout-icon.png";
	private final String LOGOUT_ICN_GLOW = "logout-icon-glow.png";
	private final String SECURITY_ICN = "security_icn.png";
	
	/**
	 * Package-private because only {@link Resources} should instantiate this
	 * class.
	 */
	Images() {
		String packageName = this.getClass().getPackage().getName();
		packageName = packageName.replace('.', '/');
		PATH = packageName + "/";
	}
	
	public ImageIcon arrowForward() {
		return new ImageIcon(getResource(ARROW_FORWARD));
	}
	
	public ImageIcon arrowBack() {
		return new ImageIcon(getResource(ARROW_BACK));
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
	
	public ImageIcon downloadFile() {
		return new ImageIcon(getResource(FILE_DOWN_ICN));
	}
	
	/**
	 * @return mouse over icon to add a file
	 */
	public ImageIcon downloadFileGlow() {
		return new ImageIcon(getResource(FILE_DOWN_ICN_GLOW));
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
	

	private URL getResource(String name) {
		return this.getClass().getClassLoader().getResource(PATH + name);
	}

}

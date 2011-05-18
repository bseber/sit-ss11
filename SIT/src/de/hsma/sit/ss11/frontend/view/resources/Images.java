package de.hsma.sit.ss11.frontend.view.resources;

import java.net.URL;

import javax.swing.ImageIcon;

public class Images {
	
	private final String PATH;
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
	
	/**
	 * @return a lock icon
	 */
	public ImageIcon lock() {
		return new ImageIcon(getResource(SECURITY_ICN));
	}

	private URL getResource(String name) {
		return this.getClass().getClassLoader().getResource(PATH + name);
	}

}

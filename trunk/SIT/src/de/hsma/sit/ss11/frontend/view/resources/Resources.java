package de.hsma.sit.ss11.frontend.view.resources;

public class Resources {

	private static Resources instance = new Resources();
	
	private Messages constants;
	private Images images;
	
	public static Resources getInstance() {
		return instance;
	}
	
	/**
	 * Access to label texts etc
	 * 
	 * @return
	 */
	public Messages messages() {
		if (constants == null) {
			constants = new Messages();
		}
		return constants;
	}

	/**
	 * Access to icons
	 * 
	 * @return
	 */
	public Images images() {
		if (images == null) {
			images = new Images();
		}
		return images;
	}

}

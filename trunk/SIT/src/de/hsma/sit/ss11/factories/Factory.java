package de.hsma.sit.ss11.factories;

import de.hsma.sit.ss11.services.FileInfoService;
import de.hsma.sit.ss11.services.KeyBoxService;
import de.hsma.sit.ss11.services.SecurityService;
import de.hsma.sit.ss11.services.SecurityServiceImpl;
import de.hsma.sit.ss11.services.UserService;

public class Factory {
	
	private static Factory factory = new Factory();
	
	public static Factory getInstance() {
		return factory;
	}
	
	public SecurityService getSecurityService() {
		return new SecurityServiceImpl();
	}
	
//	public KeyBoxService getKeyBoxService() {
//		return new KeyBoxServiceImpl();
//	}
//	
//	public UserService getUserService() {
//		return new UserServiceImpl();
//	}
//	
//	public FileInfoService getFileInfoService() {
//		return new FileInfoServiceImpl();
//	}
}

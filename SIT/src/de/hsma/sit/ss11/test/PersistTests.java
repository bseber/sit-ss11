package de.hsma.sit.ss11.test;

import java.io.File;

import de.hsma.sit.ss11.entities.AnyUser;
import de.hsma.sit.ss11.factories.Factory;

public class PersistTests {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
//			Factory.getInstance().getUserService().registerUser("test", "123", "12345678");
			
			for ( AnyUser u : Factory.getInstance().getUserService().getAllUser() ) {
				System.out.println(u.toString());
			}
			AnyUser loggedInUser = null;
			System.out.println(Factory.getInstance().getSecurityService().login("test", "falsch") instanceof AnyUser);
			System.out.println(Factory.getInstance().getSecurityService().login("test", "123") instanceof AnyUser);
			
			loggedInUser = Factory.getInstance().getSecurityService().login("test", "123");
			
			Factory.getInstance().getFileInfoService().saveFile(loggedInUser,
					new File(System.getProperty("user.dir") + File.separator + "test.txt"));
			
			Factory.getInstance().getFileInfoService().getFile(loggedInUser, 0l, "falsch");
			Factory.getInstance().getFileInfoService().getFile(loggedInUser, 0l, "12345678");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}

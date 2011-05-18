package de.hsma.sit.ss11.test;

import java.io.File;
import java.util.List;

import de.hsma.sit.ss11.entities.AnyUser;
import de.hsma.sit.ss11.entities.FileInfo;
import de.hsma.sit.ss11.factories.Factory;

public class PersistTests {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			Factory.getInstance().getUserService().registerUser("test", "123", "12345678");
			Factory.getInstance().getUserService().registerUser("test2", "321", "87654321");
			Factory.getInstance().getUserService().registerUser("test3", "132", "13572468");
			
			for ( AnyUser u : Factory.getInstance().getUserService().getAllUser() ) {
				System.out.println(u.toString());
			}
			System.out.println(Factory.getInstance().getSecurityService().login("test", "falsch") instanceof AnyUser);
			System.out.println(Factory.getInstance().getSecurityService().login("test", "123") instanceof AnyUser);
			System.out.println(Factory.getInstance().getSecurityService().login("test2", "falsch") instanceof AnyUser);
			System.out.println(Factory.getInstance().getSecurityService().login("test2", "321") instanceof AnyUser);
			System.out.println(Factory.getInstance().getSecurityService().login("test3", "falsch") instanceof AnyUser);
			System.out.println(Factory.getInstance().getSecurityService().login("test3", "132") instanceof AnyUser);
			
			AnyUser loggedInUser = Factory.getInstance().getSecurityService().login("test", "123");
			AnyUser loggedInUser2 = Factory.getInstance().getSecurityService().login("test2", "321");
			AnyUser loggedInUser3 = Factory.getInstance().getSecurityService().login("test3", "132");
			
			FileInfo fileInfo = Factory.getInstance().getFileInfoService().saveFile(loggedInUser,
					new File(System.getProperty("user.dir") + File.separator + "test.jpg"));
			
			Factory.getInstance().getFileInfoService().getFile(loggedInUser, fileInfo, "falscher");
			Factory.getInstance().getFileInfoService().getFile(loggedInUser, fileInfo, "12345678");
			
			System.out.println(Factory.getInstance().getKeyBoxService().giveKeyCopyToAnyUser(
					loggedInUser, loggedInUser2, fileInfo, "12345k78"));
			System.out.println(Factory.getInstance().getKeyBoxService().giveKeyCopyToAnyUser(
							loggedInUser, loggedInUser2, fileInfo, "12345678"));
			
			for ( AnyUser u : Factory.getInstance().getFileInfoService().getUsersWithKeyCopy(fileInfo) ) {
				System.out.println(u.toString());
			}
			
			List<FileInfo> user2FileInfo = Factory.getInstance().getFileInfoService().getAllFileInfosOfUser(loggedInUser2);
			
			Factory.getInstance().getFileInfoService().getFile(loggedInUser2, user2FileInfo.get(0), "falscher");
			Factory.getInstance().getFileInfoService().getFile(loggedInUser2, user2FileInfo.get(0), "87654321");
			
			System.out.println(Factory.getInstance().getKeyBoxService().
					removeKeyCopyFromUser(loggedInUser2, user2FileInfo.get(0)));
			
			System.out.println(Factory.getInstance().getFileInfoService().
					getFile(loggedInUser2, user2FileInfo.get(0), "falscher") instanceof File);
			user2FileInfo = Factory.getInstance().getFileInfoService().getAllFileInfosOfUser(loggedInUser2);
			System.out.println(Factory.getInstance().getFileInfoService().
					getFile(loggedInUser2, user2FileInfo.get(0), "87654321") instanceof File);
			
//			Factory.getInstance().getFileInfoService().deleteFile(fileInfo);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}

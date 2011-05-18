package de.hsma.sit.ss11.services;

import java.util.List;

import de.hsma.sit.ss11.entities.AnyUser;

public interface UserService {

	public AnyUser getUser(String name);
	
	public List<AnyUser> getAllUser();
	
	public void registerUser(String name, String password, String privatekey);
	
}

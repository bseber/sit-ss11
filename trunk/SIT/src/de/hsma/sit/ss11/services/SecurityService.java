package de.hsma.sit.ss11.services;

import de.hsma.sit.ss11.entities.AnyUser;

public interface SecurityService {
	
	public AnyUser login(String username, String password);
}

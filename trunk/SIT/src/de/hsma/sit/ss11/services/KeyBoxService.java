package de.hsma.sit.ss11.services;

import de.hsma.sit.ss11.entities.AnyUser;
import de.hsma.sit.ss11.entities.FileInfo;

public interface KeyBoxService {

	public void giveKeyCopyToAnyUser(AnyUser user, FileInfo fileInfo);
	
	public boolean removeKeyCopyFromUser(AnyUser user, FileInfo fileInfo);
	
}

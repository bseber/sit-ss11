package de.hsma.sit.ss11.services;

import de.hsma.sit.ss11.entities.AnyUser;
import de.hsma.sit.ss11.entities.FileInfo;

public interface KeyBoxService {

	public boolean giveKeyCopyToAnyUser(AnyUser owner, AnyUser user, FileInfo fileInfo, String privatePWFromOwner);
	
	public boolean removeKeyCopyFromUser(AnyUser user, FileInfo fileInfo);
	
}

package de.hsma.sit.ss11.services;

import de.hsma.sit.ss11.entities.AnyUser;
import de.hsma.sit.ss11.entities.FileInfo;

public interface KeyBoxService {

	boolean giveKeyCopyToAnyUser(AnyUser owner, AnyUser user, FileInfo fileInfo, String privatePWFromOwner);
	
	boolean removeKeyCopyFromUser(AnyUser user, FileInfo fileInfo);
	
	boolean userHasKeyCopy(long userID, FileInfo file);
	
}

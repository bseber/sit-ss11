package de.hsma.sit.ss11.services;

import java.io.File;
import java.util.List;

import de.hsma.sit.ss11.entities.AnyUser;
import de.hsma.sit.ss11.entities.FileInfo;

public interface FileInfoService {
	
	public boolean deleteFile(FileInfo fileInfo);
	
	public FileInfo saveFile(AnyUser user, File decryptedFile) throws Exception;
	
	/**
	 * Gibt die verschl�sselte Datei entschl�sselt zur�ck.
	 * 
	 * @param user Benutzer, der die Datei �ffnet
	 * @param fileId file-id der verschl�sselten Datei
	 * @param pwPrivate Passwort mit dem der Private-Key des Benutzers verschl�sselt ist
	 * 
	 * @return entschl�sselte Datei
	 */
	public File getFile(AnyUser user, Long id, String pwPrivate);
	
	public List<AnyUser> getUsersWithKeyCopy(FileInfo fileInfo);
}

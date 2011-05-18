package de.hsma.sit.ss11.services;

import java.io.File;
import java.util.List;

import de.hsma.sit.ss11.entities.AnyUser;
import de.hsma.sit.ss11.entities.FileInfo;

public interface FileInfoService {
	
	public boolean deleteFile(FileInfo fileInfo);
	
	public FileInfo saveFile(AnyUser user, File decryptedFile) throws Exception;
	
	/**
	 * Gibt die verschlüsselte Datei entschlüsselt zurück.
	 * 
	 * @param user Benutzer, der die Datei öffnet
	 * @param fileInfo der verschlüsselten Datei
	 * @param pwPrivate Passwort mit dem der Private-Key des Benutzers verschlüsselt ist
	 * 
	 * @return entschlüsselte Datei
	 */
	public File getFile(AnyUser user, FileInfo fileInfo, String pwPrivate);
	
	public List<AnyUser> getUsersWithKeyCopy(FileInfo fileInfo);
	
	public List<FileInfo> getAllFileInfosOfUser(AnyUser user);
}

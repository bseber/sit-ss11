package de.hsma.sit.ss11.services;

import java.io.File;
import java.util.List;

import de.hsma.sit.ss11.entities.AnyUser;
import de.hsma.sit.ss11.entities.FileInfo;

public interface FileInfoService {
	
	public void deleteFile(FileInfo fileInfo);
	
	public FileInfo saveFile(AnyUser user, File decryptedFile) throws Exception;
	
	public File getFileById(Long id);
	
	public List<AnyUser> getUsersWithKeyCopy(FileInfo fileInfo);
}

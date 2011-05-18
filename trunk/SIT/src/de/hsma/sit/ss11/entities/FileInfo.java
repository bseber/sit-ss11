package de.hsma.sit.ss11.entities;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: FileInfo
 *
 */
@Entity
public class FileInfo implements Serializable {


	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long fileID;
	private String fileName;
	private String saveName;
	private String encryptedKeyCopy;
	private boolean master;
	private long userID;
	
	

	public FileInfo() {
		super();
	}   
	public long getFileID() {
		return this.fileID;
	}

	public void setFileID(long fileID) {
		this.fileID = fileID;
	}   
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}   
	public String getSaveName() {
		return this.saveName;
	}

	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}   
	public String getEncryptedKeyCopy() {
		return this.encryptedKeyCopy;
	}

	public void setEncryptedKeyCopy(String encryptedKeyCopy) {
		this.encryptedKeyCopy = encryptedKeyCopy;
	}   
	public boolean getMaster() {
		return this.master;
	}

	public void setMaster(boolean master) {
		this.master = master;
	}
	public void setUserID(long userID) {
		this.userID = userID;
	}
	public long getUserID() {
		return userID;
	}
	
	
	
   
}

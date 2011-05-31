package de.hsma.sit.ss11.entities;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: FileInfo
 *
 */
@Entity
public class FileInfo implements Comparable<FileInfo>, Serializable {


	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long fileID;
	private String fileName;
	private String saveName;
	private byte[] encryptedKeyCopy;
	private boolean master;
	private long userID;
	
	@Override
	public String toString() {
		return "id: " + fileID + " fileName: " + fileName + " saveName: " + saveName +
		" encryptedKeyCopy: "+ new String(encryptedKeyCopy) + " master: " + master +
		" user-id: " + userID;
	}

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
	public byte[] getEncryptedKeyCopy() {
		return this.encryptedKeyCopy;
	}

	public void setEncryptedKeyCopy(byte[] encryptedKeyCopy) {
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

	@Override
	public int compareTo(FileInfo o) {
		return this.fileName.compareTo(o.getFileName());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (fileID ^ (fileID >>> 32));
		result = prime * result
				+ ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result
				+ ((saveName == null) ? 0 : saveName.hashCode());
		result = prime * result + (int) (userID ^ (userID >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileInfo other = (FileInfo) obj;
		if (fileID != other.fileID)
			return false;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (saveName == null) {
			if (other.saveName != null)
				return false;
		} else if (!saveName.equals(other.saveName))
			return false;
		if (userID != other.userID)
			return false;
		return true;
	}
	
}

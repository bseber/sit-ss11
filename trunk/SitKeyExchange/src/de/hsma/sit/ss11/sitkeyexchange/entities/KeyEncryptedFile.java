package de.hsma.sit.ss11.sitkeyexchange.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: KeyEncryptedFile
 *
 */
@Entity
public class KeyEncryptedFile implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int keyid;
	private int encryptedFileId;
	private static final long serialVersionUID = 1L;

	public KeyEncryptedFile() {
		super();
	}   
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	public int getKeyid() {
		return this.keyid;
	}

	public void setKeyid(int keyid) {
		this.keyid = keyid;
	}   
	public int getEncryptedFileId() {
		return this.encryptedFileId;
	}

	public void setEncryptedFileId(int encryptedFileId) {
		this.encryptedFileId = encryptedFileId;
	}
   
}

package de.hsma.sit.ss11.sitkeyexchange.entities;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: EncryptedKeybox
 *
 */
@Entity
public class EncryptedKeybox implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private boolean encryptedKeyIdKeyValueMasterBoolString;
	private String hash;
	private static final long serialVersionUID = 1L;

	public EncryptedKeybox() {
		super();
	}   
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	public boolean getEncryptedKeyIdKeyValueMasterBoolString() {
		return this.encryptedKeyIdKeyValueMasterBoolString;
	}

	public void setEncryptedKeyIdKeyValueMasterBoolString(boolean encryptedKeyIdKeyValueMasterBoolString) {
		this.encryptedKeyIdKeyValueMasterBoolString = encryptedKeyIdKeyValueMasterBoolString;
	}   
	public String getHash() {
		return this.hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}
   
}

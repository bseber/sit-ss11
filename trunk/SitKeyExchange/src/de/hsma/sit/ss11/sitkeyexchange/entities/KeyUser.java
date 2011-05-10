package de.hsma.sit.ss11.sitkeyexchange.entities;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: User
 *
 */
@Entity
public class KeyUser implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String password;
	private String name;
	private String receivedEncryptedKeyIdsString;
	private static final long serialVersionUID = 1L;

	public KeyUser() {
		super();
	}   
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}   
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}   
	public String getReceivedEncryptedKeyIdsString() {
		return this.receivedEncryptedKeyIdsString;
	}

	public void setReceivedEncryptedKeyIdsString(String receivedEncryptedKeyIdsString) {
		this.receivedEncryptedKeyIdsString = receivedEncryptedKeyIdsString;
	}
   
}

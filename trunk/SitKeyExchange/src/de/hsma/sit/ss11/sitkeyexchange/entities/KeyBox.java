package de.hsma.sit.ss11.sitkeyexchange.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: KeyBox
 *
 */
@Entity
public class KeyBox implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int user_id;
	private int encryptedKeyBoxId;
	private static final long serialVersionUID = 1L;

	public KeyBox() {
		super();
	}   
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	public int getUser_id() {
		return this.user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}   
	public int getEncryptedKeyBoxId() {
		return this.encryptedKeyBoxId;
	}

	public void setEncryptedKeyBoxId(int encryptedKeyBoxId) {
		this.encryptedKeyBoxId = encryptedKeyBoxId;
	}
   
}

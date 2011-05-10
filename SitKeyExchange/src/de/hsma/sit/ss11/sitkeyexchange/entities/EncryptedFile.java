package de.hsma.sit.ss11.sitkeyexchange.entities;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: EncryptedFile
 *
 */
@Entity
public class EncryptedFile implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String hash;
	private String data;
	private static final long serialVersionUID = 1L;

	public EncryptedFile() {
		super();
	}   
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}   
	public String getHash() {
		return this.hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}   
	public String getData() {
		return this.data;
	}

	public void setData(String data) {
		this.data = data;
	}
   
}

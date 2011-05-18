package de.hsma.sit.ss11.entities;

import java.io.Serializable;
import java.lang.String;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: AnyUser
 *
 */
@Entity
public class AnyUser implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private String pwhash;
	private String publicKey;
	private String privateKeyHash;
	

	
	public AnyUser() {
		super();
	}
	
	public void setId(long id) {
		this.id = id;
	}
	public long getId() {
		return id;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}   
	public String getPwhash() {
		return this.pwhash;
	}

	public void setPwhash(String pwhash) {
		this.pwhash = pwhash;
	}   
	public String getPublicKey() {
		return this.publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}   
	public String getPrivateKeyHash() {
		return this.privateKeyHash;
	}

	public void setPrivateKeyHash(String privateKeyHash) {
		this.privateKeyHash = privateKeyHash;
	}

   
}

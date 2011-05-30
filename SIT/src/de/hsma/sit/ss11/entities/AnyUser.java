package de.hsma.sit.ss11.entities;

import java.io.Serializable;
import java.lang.String;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: AnyUser
 *
 */
@Entity
public class AnyUser implements Comparable<AnyUser>, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private String pwhash;
	private byte[] publicKey;
	private String privateKeyHash;
	private byte[] encryptedPrivateKey;
	
	@Override
	public String toString() {
		return "id: " + id + " name: " + name + " pwhash: " + pwhash +
		" publicKey: "+ new String(publicKey) + " privateKeyHash: " + privateKeyHash +
		" encrypted private key: " + new String(encryptedPrivateKey);
	}
	
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
	public byte[] getPublicKey() {
		return this.publicKey;
	}

	public void setPublicKey(byte[] publicKey) {
		this.publicKey = publicKey;
	}   
	public String getPrivateKeyHash() {
		return this.privateKeyHash;
	}

	public void setPrivateKeyHash(String privateKeyHash) {
		this.privateKeyHash = privateKeyHash;
	}

	public byte[] getEncryptedPrivateKey() {
		return encryptedPrivateKey;
	}

	public void setEncryptedPrivateKey(byte[] encryptedPrivateKey) {
		this.encryptedPrivateKey = encryptedPrivateKey;
	}

	@Override
	public int compareTo(AnyUser o) {
		return this.name.compareTo(o.getName());
	}
}

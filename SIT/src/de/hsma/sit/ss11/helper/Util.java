package de.hsma.sit.ss11.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import de.hsma.sit.ss11.entities.AnyUser;
import de.hsma.sit.ss11.entities.FileInfo;

public class Util {
	
	public static String ENCRYPTED_FILES_SAVE_PATH = System.getProperty("user.dir") + File.separator +
														"enc_files" + File.separator;
	public static String DECRYPTED_FILES_SAVE_PATH = System.getProperty("user.dir") + File.separator +
	"dec_files" + File.separator;
	
	/**
	 * Gibt die MD5 Checksumme einer Datei zurück.
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static String getMD5Checksum(File file) throws Exception {
		
		InputStream is = null;
		try {
			byte[] buffer = new byte[1024];
			MessageDigest md = MessageDigest.getInstance("MD5");
			is = new FileInputStream(file);
			
			int numRead;
			// Liest mit 1024 byte pro Durchlauf die Daten ein und
			// generiert damit die MD5 Checksumme der Datei
		    do {
		         numRead = is.read(buffer);
		         if (numRead > 0) {
		           md.update(buffer, 0, numRead);
		           }
		     } while (numRead != -1);
		    
		    byte[] b = md.digest();
		    
		    return byteArrayToHexString(b);
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			if ( is!=null )
				is.close();
		}
	}
	
	/**
	 * Gibt die MD5 Checksumme eines Strings zurück.
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String getMD5Checksum(String str) throws Exception {
		
		MessageDigest md = MessageDigest.getInstance("MD5");
		return byteArrayToHexString(md.digest(str.getBytes()));
	}
	
	/**
	 * Konvertiert ein Byte-Array in einen HEX String.
	 * 
	 * @param b
	 * @return
	 */
	private static String byteArrayToHexString(byte[] b) {
		
	    String result = "";
	    for (int i=0; i < b.length; i++) {
	      result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
	    }
	    return result;
	}

	/**
	 * Lädt den öffentlichen 1024 Bit RSA Schlüssel des Masters aus der Datei
	 * und gibt ihn zurück.
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Key getMasterPublicKey() throws Exception {
		
		InputStream is = null;
		
		try {
			File publicMasterKeyFile = new File(System.getProperty("user.dir") + File.separator +
					"master_public_key.dat");
			
			is = new FileInputStream(publicMasterKeyFile);
			byte[] masterPublicKeyBytes = new byte[(int) publicMasterKeyFile.length()];

			// bytes werden eingelesen
			is.read(masterPublicKeyBytes);
			
			// das EncodingKeySpec des Public-Keys wird erzeugt
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(masterPublicKeyBytes);
			
			// der öffentliche Schlüssel des Masters wird aus den geladenen Daten rekonstruiert
			Key masterPublicKey = KeyFactory.getInstance("RSA").generatePublic(keySpec);
			
			return masterPublicKey;
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if (is != null)
				is.close();
		}
	}
	
	/**
	 * Entschlüsselt ein Byte Array mit dem DES Algorithmus.
	 * 
	 * @param encBytes verschlüsselte Bytes
	 * @param keyBytes DES Schlüssel zum entschlüsseln der Bytes
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidKeyException
	 */
	public static byte[] decryptWithDES(byte[] encBytes, byte[] keyBytes) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
		
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyBytes, "DES"));
		return cipher.doFinal(encBytes);
	}
	
	/**
	 * Entschlüsselt ein Byte Array mit dem RSA Algorithmus.
	 * 
	 * @param encBytes verschlüsselte Bytes
	 * @param privateKeyBytes Bytes des Privat-Keys
	 * @return
	 * @throws InvalidKeyException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 */
	public static byte[] decryptWithRSAAndPrivateKey(byte[] encBytes, byte[] privateKeyBytes) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException {
		
		// das EncodingKeySpec des Private-Keys wird erzeugt
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
		// der private Schlüssel des Benutzers wird rekonstruiert
		Key privateKey = KeyFactory.getInstance("RSA").generatePrivate(keySpec);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		// die verschlüsselten Bytes werden mit dem RSA Private-Key entschlüsselt
		return cipher.doFinal(encBytes);
	}
	
	public static byte[] getDecryptedDocKeyFromHeader(FileInfo fileInfo, AnyUser user, String pwPrivate) {

		InputStream is = null;
		byte[] decryptedDocKey = null;
		try {
			is = new FileInputStream(Util.ENCRYPTED_FILES_SAVE_PATH + fileInfo.getSaveName());
			byte[] encryptedDocKey = new byte[128];
			// liest den verschlüsselten Dokumentenschlüssel des Masters -> wird verworfen
			is.read(encryptedDocKey);
			// liest den verschlüsselten Dokumentenschlüssel des Benutzers
			is.read(encryptedDocKey);
			// entschlüsselt den Private-Key des Benutzers
			byte[] decryptedPrivateKey = Util.decryptWithDES(user.getEncryptedPrivateKey(), pwPrivate.getBytes());
			// entschlüsselt den Dokumentenschlüssel
			decryptedDocKey = Util.decryptWithRSAAndPrivateKey(encryptedDocKey, decryptedPrivateKey);
			decryptedPrivateKey = null;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return decryptedDocKey;
	}
}

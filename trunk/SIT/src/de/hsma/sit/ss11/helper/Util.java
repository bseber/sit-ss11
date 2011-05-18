package de.hsma.sit.ss11.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.spec.X509EncodedKeySpec;

public class Util {
	
	public static String ENCRYPTED_FILES_SAVE_PATH = 
		System.getProperty("java.class.path").substring(0,System.getProperty("java.class.path").length()-3) +
		"enc_files" + File.separator;
	
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
			File publicMasterKeyFile = new File(
					System.getProperty("java.class.path").substring(0, System.getProperty("java.class.path").length()-3)
					+ "master_public_key.dat");
			
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
}

package de.hsma.sit.ss11.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import de.hsma.sit.ss11.entities.AnyUser;
import de.hsma.sit.ss11.entities.FileInfo;
import de.hsma.sit.ss11.helper.Util;

public class FileInfoServiceImpl implements FileInfoService {
	
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("SIT");

	@Override
	public boolean deleteFile(FileInfo fileInfo) {
		
		// löscht die verschlüsselte Datei aus dem Filesystem
		File file = new File(Util.ENCRYPTED_FILES_SAVE_PATH + fileInfo.getSaveName());
		
		// Löschen aus dem Filesystem fehlgeschlagen ?
		if ( !file.delete() ) {
			// TODO
			return false;
		}
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		// löscht die verschlüsselte Datei aus der Datenbank
		try {
			em.remove(fileInfo);
			return true;
		}
		// löschen aus der Datenbank fehlgeschlagen
		catch (Exception e) {
			// TODO
			e.printStackTrace();
			return false;
		}
		finally {
			em.getTransaction().commit();
			em.close();
		}
	}

	@Override
	public FileInfo saveFile(AnyUser user, File decryptedFile) throws Exception {

		/**
		 *  Speichert die Datei verschlüsselt mit Header im Filesystem.
		 *  
		 *  Header:
		 *  - doc_key_encrypted_with_master_public_key (128 Bytes)
		 *  - doc_key_encrypted_with_users_public_key (128 Bytes)
		 *  - decrypted_file_hash (32 Bytes)
		 *  Body:
		 *  - file_data_encrypted_with_doc_key
		 */
		KeyGenerator kg = KeyGenerator.getInstance("DES");
		kg.init(56);
		// zufälliger 56 Bit DES Dokumentenschlüssel wird generiert
		SecretKey docKey = kg.generateKey();

		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, Util.getMasterPublicKey());
		// Dokumentenschlüssel wird mit dem öffentlichen Master Schlüssel verschlüsselt
		byte[] docKeyEncWithMasterPublicKey = cipher.doFinal(docKey.getEncoded());
		
		// das EncodingKeySpec des Public-Keys wird erzeugt
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(user.getPublicKey());
		// der öffentliche Schlüssel des Benutzers wird rekonstruiert
		Key userPublicKey = KeyFactory.getInstance("RSA").generatePublic(keySpec);
		cipher.init(Cipher.ENCRYPT_MODE, userPublicKey);
		// Dokumentenschlüssel wird mit dem öffentlichen Schlüssel des Benutzers verschlüsselt
		byte[] docKeyEncWithUserPublicKey = cipher.doFinal(docKey.getEncoded());

		
		String fileName = decryptedFile.getName();
		
		// Es wird ein Dateiname, der noch nicht existiert generiert.
		File encryptedSaveFile = generateNonExistingFilename(new File(Util.ENCRYPTED_FILES_SAVE_PATH + fileName));
		
		// Erzeugt MD5-Checksumme der unverschlüsselten Datei als Hex-String
		String md5ChecksumDecryptedFile = Util.getMD5Checksum(decryptedFile);
		
		// speichert den Header der verschlüsselten Datei im Filesystem
		OutputStream os=null;
		try {
			os = new FileOutputStream(encryptedSaveFile);
			os.write(docKeyEncWithMasterPublicKey);
			os.write(docKeyEncWithUserPublicKey);
			os.write(md5ChecksumDecryptedFile.getBytes());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (os != null)
					os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		InputStream is=null;
		os=null;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(docKey.getEncoded(), "DES"));
			is = new FileInputStream(decryptedFile);
			// verschlüsseltes Dokument wird an den Header gehängt
			os = new FileOutputStream(encryptedSaveFile, true);
			CipherOutputStream cos = new CipherOutputStream(os, cipher);
			byte[] readedBytes = new byte[1024];
			int readedByteCount = 0;
			// Dokument wird mit dem Dokumentenschlüssel verschlüsselt im Filesystem abgelegt
			do {
				readedByteCount = is.read(readedBytes);
				cos.write(readedBytes);
			}
			while(readedByteCount != -1);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (is != null)
					is.close();
				if (os != null)
					os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// Dokumentenschlüssel wird aus dem Arbeitsspeicher entfernt TODO ?? ka obs geht
		docKey = null;
	
		return saveFileInfo(decryptedFile.getName(), encryptedSaveFile.getName(), user.getId());
	}
	
	/**
	 * Es wird ein Dateiname, der noch nicht existiert generiert.
	 * 
	 * @param file
	 * @return
	 */
	private File generateNonExistingFilename(File file) {
		
		Random rnd = new Random();
		while ( file.exists() ) {
			// hängt eine Zufallszahl zwischen 0 und 9 an den Dateinamen, falls die Datei
			// bereits existiert
			file.renameTo(new File(file.getAbsolutePath() + rnd.nextInt(10)));
		}
		return file;
	}

	/**
	 * Speichert die Datei-Infos in der Datenbank und
	 * gibt sie zurück.
	 * 
	 * @param filename der unverschlüsselten Datei
	 * @param savename der verschlüsselten Datei
	 * @param userid des Dateibesitzers(Master)
	 * 
	 * @return
	 */
	private FileInfo saveFileInfo(String filename, String savename, long userid) {
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		// erzeugt das Datei-Info Objekt
		FileInfo fi = new FileInfo();
		fi.setFileName(filename);
		fi.setSaveName(savename);
		fi.setMaster(true);
		fi.setUserID(-1l);
		fi.setEncryptedKeyCopy(new byte[0]);
		
		// speichert die Datei-Infos in der Datenbank
		em.persist(fi);
		em.getTransaction().commit();
		em.close();
		
		return fi;
	}

	@Override
	public File getFile(AnyUser user, Long fileId, String pwPrivate) {
		
		if ( !validatePrivateKeyDecryptionPassword(user, pwPrivate) ) {
			return null;
		}

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		Query q = em.createQuery ("SELECT fileInfo FROM FileInfo fileInfo WHERE fileInfo.fileID = :fileID");
		q.setParameter("fileID", fileId);
		
		FileInfo fileInfo=null;
		try {
			// Datei-Infos werden geladen
			fileInfo = (FileInfo) q.getSingleResult();
			em.getTransaction().commit();
		}
		catch(NoResultException e){
			return null;
		}
		finally{
			em.close();
		}
		
		/**
		 *  Datei Header wird geladen
		 */
		
		byte[] decryptedDocKey = new byte[128];
			
		// Benutzer ist der Master der Datei 
		// -> Dokumentenschlüssel wird aus dem Header entschlüsselt
		if ( fileInfo.getMaster() ) {
			decryptedDocKey = Util.getDecryptedDocKeyFromHeader(fileInfo, user, pwPrivate);
		}			
		// Benutzer hat eine verschlüsselte Kopie des Dokumentenschlüssels
		// -> Dokumentenschlüssel wird aus der Kopie entschlüsselt
		else {
			decryptedDocKey = getDecryptedDocKeyFromDBCopy(fileInfo, user, pwPrivate);
		}
		
		File decryptedFile = getDecryptedFile(decryptedDocKey, fileInfo);
		// validiert die MD5 Checksumme der entschlüsselten Datei
		// mit der MD5 Checksumme, die vor dem verschlüsseln der Datei erstellt wurde
		try {
			if ( getFileHashFromHeader(fileInfo).equals(Util.getMD5Checksum(decryptedFile)) ) {
				// Datei wurde erfolgreich entschlüsselt
				return decryptedFile;
			}
			else {
				return null;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private File getDecryptedFile(byte[] decryptedDocKey, FileInfo fileInfo) {
		
		InputStream is=null;
		OutputStream os=null;
		File decryptedDocFile = null;
		try {
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptedDocKey, "DES"));
			// das File des entschlüsselten Dokuments
			decryptedDocFile = generateNonExistingFilename(
					new File(Util.DECRYPTED_FILES_SAVE_PATH + fileInfo.getSaveName()));
			is = new FileInputStream(new File(Util.ENCRYPTED_FILES_SAVE_PATH + fileInfo.getSaveName()));
			// verschlüsseltes Dokument wird an den Header gehängt
			os = new FileOutputStream(decryptedDocFile);
			CipherOutputStream cos = new CipherOutputStream(os, cipher);
			byte[] readBytes = new byte[1024];
			int readByteCount = 0;
			// Dokument wird mit dem Dokumentenschlüssel entschlüsselt
			do {
				readByteCount = is.read(readBytes);
				cos.write(readBytes);
			}
			while(readByteCount != -1);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (is != null)
					is.close();
				if (os != null)
					os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return decryptedDocFile;
	}

	/**
	 * Gibt true zurück, wenn das Private_Passwort mit dem der Private-Key verschlüsselt ist
	 * richtig ist.
	 * 
	 * @param user 
	 * @param pwPrivate
	 * @return
	 */
	private boolean validatePrivateKeyDecryptionPassword(AnyUser user, String pwPrivate) {
		
		boolean isPWPrivateValid = false;
		
		try {
			// entschlüsselt den Private-Key
			byte[] decryptedPrivateKey = Util.decryptWithDES(user.getEncryptedPrivateKey(), pwPrivate.getBytes());
			String decryptedPrivateKeyHash = Util.getMD5Checksum(new String(decryptedPrivateKey));
			
			isPWPrivateValid = decryptedPrivateKeyHash.equals(user.getPrivateKeyHash());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return isPWPrivateValid;
	}

	private byte[] getDecryptedDocKeyFromDBCopy(FileInfo fileInfo, AnyUser user, String pwPrivate) {
		
		byte[] decryptedDocKey = null;
		try {
			// entschlüsselt den Private-Key des Benutzers
			byte[] decryptedPrivateKey = Util.decryptWithDES(user.getEncryptedPrivateKey(), pwPrivate.getBytes());
			// entschlüsselt den Dokumentenschlüssel
			decryptedDocKey = Util.decryptWithRSAAndPrivateKey(fileInfo.getEncryptedKeyCopy(), decryptedPrivateKey);
			decryptedPrivateKey = null;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return decryptedDocKey;
	}
	
	private String getFileHashFromHeader(FileInfo fileInfo) {

		InputStream is = null;
		String fileHash = null;
		try {
			is = new FileInputStream(Util.ENCRYPTED_FILES_SAVE_PATH + fileInfo.getSaveName());
			byte[] encryptedKeys = new byte[128];
			// liest den verschlüsselten Dokumentenschlüssel des Masters -> wird verworfen
			is.read(encryptedKeys);
			// liest den verschlüsselten Dokumentenschlüssel des Benutzers -> wird verworfen
			is.read(encryptedKeys);
			byte[] fileHashBytes = new byte[32];
			// liest die MD5 Checksummer der Datei aus dem Header
			is.read(fileHashBytes);
			fileHash = new String(fileHashBytes);
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
		return fileHash;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AnyUser> getUsersWithKeyCopy(FileInfo fileInfo) {

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		// lädt alle Datei-Infos der Schlüsselkopienm !!! außer die des Besitzers der Datei !!!
		Query q = em.createQuery ("SELECT fileInfo FROM FileInfo fileInfo WHERE fileInfo.saveName = :saveName AND" +
				" fileInfo.master = false");
		q.setParameter("saveName", fileInfo.getSaveName());
		
		List<FileInfo> keyCopyFileInfos=null;
		try {
			// Datei-Infos werden geladen
			keyCopyFileInfos = q.getResultList();
		}
		catch(NoResultException e){
			return null;
		}
		finally{
			em.getTransaction().commit();
			em.close();
		}
		
		// lädt alle Benutzer mit einem Schlüsselkopie aus der Datenbank
		List<AnyUser> usersWithKeyCopy = new LinkedList<AnyUser>();
		q = em.createQuery ("SELECT anyUser FROM AnyUser anyUser WHERE anyUser.id = :userId");
		for ( FileInfo fi : keyCopyFileInfos ) {
			
			q.setParameter("userId", fi.getUserID());
			
			try {
				usersWithKeyCopy.add((AnyUser)q.getSingleResult());
			}
			catch(NoResultException e){
				return null;
			}
			finally{
				em.getTransaction().commit();
				em.close();
			}
		}
		
		return usersWithKeyCopy;
	}

}

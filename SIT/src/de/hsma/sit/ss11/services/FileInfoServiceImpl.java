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
import java.util.List;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import de.hsma.sit.ss11.entities.AnyUser;
import de.hsma.sit.ss11.entities.FileInfo;
import de.hsma.sit.ss11.helper.Util;

public class FileInfoServiceImpl implements FileInfoService {
	
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("SIT");

	@Override
	public void deleteFile(FileInfo fileInfo) {
		
		// löscht die verschlüsselte Datei aus dem Filesystem
		File file = new File(Util.ENCRYPTED_FILES_SAVE_PATH + fileInfo.getSaveName());
		
		// Löschen aus dem Filesystem fehlgeschlagen ?
		if ( !file.delete() ) {
			// TODO
			return;
		}
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		// löscht die verschlüsselte Datei aus der Datenbank
		try {
			em.remove(fileInfo);
		}
		// löschen aus der Datenbank fehlgeschlagen
		catch (Exception e) {
			// TODO
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
		 *  - doc_key_encrypted_with_master_public_key
		 *  - doc_key_encrypted_with_users_public_key
		 *  - decrypted_file_hash
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
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(user.getPublicKey().getBytes());
		// der öffentliche Schlüssel des Benutzers wird rekonstruiert
		Key userPublicKey = KeyFactory.getInstance("RSA").generatePublic(keySpec);
		cipher.init(Cipher.ENCRYPT_MODE, userPublicKey);
		// Dokumentenschlüssel wird mit dem öffentlichen Schlüssel des Benutzers verschlüsselt
		byte[] docKeyEncWithUserPublicKey = cipher.doFinal(docKey.getEncoded());

		
		String fileName = decryptedFile.getName();
		
		// Es wird ein Dateiname, der noch nicht existiert generiert.
		File encryptedSaveFile = new File(Util.ENCRYPTED_FILES_SAVE_PATH + fileName);
		Random rnd = new Random();
		while ( encryptedSaveFile.exists() ) {
			// hängt eine Zufallszahl zwischen 0 und 9 an den Dateinamen, falls die Datei
			// bereits existiert
			encryptedSaveFile.renameTo(new File(encryptedSaveFile.getAbsolutePath() + rnd.nextInt(10)));
		}
		
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
		
		// speichert die Datei-Infos in der Datenbank
		em.persist(fi);
		em.getTransaction().commit();
		em.close();
		
		return fi;
	}

	@Override
	public File getFileById(Long id) {

		return null;
	}

	@Override
	public List<AnyUser> getUsersWithKeyCopy(FileInfo fileInfo) {

		return null;
	}

}

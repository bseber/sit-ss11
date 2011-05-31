package de.hsma.sit.ss11.services;

import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;

import javax.crypto.Cipher;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import de.hsma.sit.ss11.entities.AnyUser;
import de.hsma.sit.ss11.entities.FileInfo;
import de.hsma.sit.ss11.helper.Util;

public class KeyBoxServiceImpl implements KeyBoxService {

	public EntityManagerFactory emf = Persistence.createEntityManagerFactory("SIT");
	
	@Override
	public boolean giveKeyCopyToAnyUser(AnyUser owner, AnyUser user, FileInfo fileInfo, String privatePWFromOwner) {
		EntityManager em = emf.createEntityManager();
		
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(user.getPublicKey());
			// der öffentliche Schlüssel des Benutzers wird rekonstruiert
			Key userPublicKey = KeyFactory.getInstance("RSA").generatePublic(publicKeySpec);
			cipher.init(Cipher.ENCRYPT_MODE, userPublicKey);
			
			em.getTransaction().begin();
			
			FileInfo fileCopy = new FileInfo();
			fileCopy.setEncryptedKeyCopy(cipher.doFinal(Util.getDecryptedDocKeyFromHeader(fileInfo, owner, privatePWFromOwner)));
			fileCopy.setFileName(fileInfo.getFileName());
			fileCopy.setMaster(false);
			fileCopy.setSaveName(fileInfo.getSaveName());
			fileCopy.setUserID(user.getId());
			em.persist(fileCopy);
			
			em.getTransaction().commit();
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		finally {
			em.close();
		}
	}

	@Override
	public boolean removeKeyCopyFromUser(AnyUser user, FileInfo fileInfo) {

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		Query q = em.createQuery ("SELECT fileInfo FROM FileInfo fileInfo WHERE fileInfo.userID = :userID" +
				" AND fileInfo.saveName = :saveName");
		q.setParameter("userID", user.getId());
		q.setParameter("saveName", fileInfo.getSaveName());
		
		try{
			FileInfo fi = (FileInfo) q.getSingleResult();
			if(fi != null){
				em.remove(fi);
				em.getTransaction().commit();
				return true;
			}
			else{
				return false;
			}
		}
		catch(NoResultException e){
			return false;
		}
		finally {
			em.close();
		}
	}

	@Override
	public boolean userHasKeyCopy(long userID, FileInfo file) {
		// TODO Auto-generated method stub
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		Query q = em.createQuery ("SELECT fileInfo FROM FileInfo fileInfo WHERE fileInfo.userID = :userID" +
				" AND fileInfo.saveName = :saveName");
		q.setParameter("userID", userID);
		q.setParameter("saveName", file.getSaveName());
		
		try {
			List<?> result = q.getResultList();
			return !result.isEmpty();
		} finally {
			em.close();
		}
	}
	
	
	
}

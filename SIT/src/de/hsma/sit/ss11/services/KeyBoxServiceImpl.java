package de.hsma.sit.ss11.services;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
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
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(user.getPublicKey(), "RSA"));
			
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
		
		Query q = em.createQuery ("SELECT fileInfo FROM FileInfo fileInfo WHERE fileInfo.userID = :userID");
		q.setParameter("userID", user.getId());
		
		try{
			FileInfo file = (FileInfo) q.getSingleResult();
			if(file != null){
				em.remove(file);
				return true;
			}else{
				return false;
			}
		}
		catch(NoResultException e){
			return false;
		}
		finally{
			em.getTransaction().commit();
			em.close();
		}
		
	}
	
	
	
}

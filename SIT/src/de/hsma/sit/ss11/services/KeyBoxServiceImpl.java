package de.hsma.sit.ss11.services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import de.hsma.sit.ss11.entities.AnyUser;
import de.hsma.sit.ss11.entities.FileInfo;

public class KeyBoxServiceImpl implements KeyBoxService {

	public EntityManagerFactory emf = Persistence.createEntityManagerFactory("SIT");
	
	@Override
	public void giveKeyCopyToAnyUser(AnyUser user, FileInfo fileInfo) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		// TODO EncryptedKeyCopy
		String encrypt = "";
		// TODO generate SaveName
		String saveName = "s";
		FileInfo fileCopy = new FileInfo();
		fileCopy.setEncryptedKeyCopy(encrypt);
		fileCopy.setFileName(fileInfo.getFileName());
		fileCopy.setMaster(false);
		fileCopy.setSaveName(saveName);
		fileCopy.setUserID(user.getId());
		em.persist(fileCopy);
		
		em.getTransaction().commit();
		
	}

	@Override
	public boolean removeKeyCopyFromUser(AnyUser user, FileInfo fileInfo) {
		// TODO Auto-generated method stub
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

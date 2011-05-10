package de.hsma.sit.ss11.sitkeyexchange.manager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import de.hsma.sit.ss11.sitkeyexchange.entities.KeyEncryptedFile;

public class KeyEncryptedFileManager {

	public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("SitKeyExchange");
	
	
	public static void saveKeyEncryptedFile(KeyEncryptedFile enFile){
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		if(enFile.getId()>0){
			em.merge(enFile);
		}else{
			em.persist(enFile);
		}
		em.getTransaction().commit();
		em.close();
	}
}

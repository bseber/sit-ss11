package de.hsma.sit.ss11.sitkeyexchange.manager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import de.hsma.sit.ss11.sitkeyexchange.entities.EncryptedFile;

public class EncryptedFileManager {

	public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("SitKeyExchange");
	
	
	public static void saveEncryptedFile(EncryptedFile file){
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		if(file.getId()>0){
			em.merge(file);
		}else{
			em.persist(file);
		}
		em.getTransaction().commit();
		em.close();
	}
	
}

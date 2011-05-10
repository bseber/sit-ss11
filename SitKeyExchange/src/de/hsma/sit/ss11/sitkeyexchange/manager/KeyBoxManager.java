package de.hsma.sit.ss11.sitkeyexchange.manager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import de.hsma.sit.ss11.sitkeyexchange.entities.KeyBox;

public class KeyBoxManager {

	public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("SitKeyExchange");
	
	
	public static void saveKeyBox(KeyBox box){
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		if(box.getId()>0){
			em.merge(box);
		}
		else{
			em.persist(box);
		}
		em.getTransaction().commit();
		em.close();
	}
}

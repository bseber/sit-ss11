package de.hsma.sit.ss11.sitkeyexchange.manager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import de.hsma.sit.ss11.sitkeyexchange.entities.KeyUser;

public class KeyUserManager {

	public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("SitKeyExchange");
	
	
	public static void saveUser(KeyUser user){
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		if(user.getId() > 0){
			em.merge(user);
		}else{
			em.persist(user);
		}
		em.getTransaction().commit();
		em.close();
	}
	
}

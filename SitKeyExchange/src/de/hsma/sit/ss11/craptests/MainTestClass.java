package de.hsma.sit.ss11.craptests;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import de.hsma.sit.ss11.sitkeyexchange.entities.KeyUser;
import de.hsma.sit.ss11.sitkeyexchange.manager.KeyUserManager;

public class MainTestClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("SitKeyExchange");
		EntityManager em = emf.createEntityManager();
		
		KeyUser user = new KeyUser();
		user.setName("Heinz");
//		em.getTransaction().begin();
//		em.persist(user);
//		KeyUserManager.saveUser(user);
		KeyUser user2 = em.find(KeyUser.class, 4);
		System.out.println(user2.getName());
//		user2.setName("AndererHeinz");
//		KeyUserManager.saveUser(user2);
		
//		em.getTransaction().commit();
		
		
	}

}

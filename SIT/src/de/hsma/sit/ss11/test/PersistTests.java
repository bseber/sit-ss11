package de.hsma.sit.ss11.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import de.hsma.sit.ss11.entities.AnyUser;
import de.hsma.sit.ss11.factories.Factory;

public class PersistTests {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Factory.getInstance().getSecurityService().login("user1", "none");
		
//		EntityManagerFactory emf = Persistence.createEntityManagerFactory("SIT");
//		EntityManager em = emf.createEntityManager();
//		
//		AnyUser user = new AnyUser();
//		user.setName("user1");
//		user.setPrivateKeyHash("###");
//		user.setPublicKey("lol");
//		user.setPwhash("none");
//		
//		em.getTransaction().begin();
//		em.persist(user);
//		em.getTransaction().commit();
//		em.close();
		

	}

}

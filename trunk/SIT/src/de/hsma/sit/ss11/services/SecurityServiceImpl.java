package de.hsma.sit.ss11.services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import de.hsma.sit.ss11.entities.AnyUser;

public class SecurityServiceImpl implements SecurityService {

	public EntityManagerFactory emf = Persistence.createEntityManagerFactory("SIT");
	
	@Override
	public AnyUser login(String username, String password) {
		// TODO Auto-generated method stub
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		Query q = em.createQuery ("SELECT anyUser FROM AnyUser anyUser WHERE anyUser.name = :username AND anyUser.pwhash = :password");
		q.setParameter("username", username);
		q.setParameter("password", password);
		
		try{
			AnyUser u = (AnyUser) q.getSingleResult();
			System.out.println(u.getName());
			return u;
		}
		catch(NoResultException e){
			return null;
		}
		finally{
			em.getTransaction().commit();
			em.close();
		}
	}

}

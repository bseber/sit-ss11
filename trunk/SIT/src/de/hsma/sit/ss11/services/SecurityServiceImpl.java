package de.hsma.sit.ss11.services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import de.hsma.sit.ss11.entities.AnyUser;
import de.hsma.sit.ss11.helper.Util;

public class SecurityServiceImpl implements SecurityService {

	public EntityManagerFactory emf = Persistence.createEntityManagerFactory("SIT");
	
	@Override
	public AnyUser login(String username, String password) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		Query q = em.createQuery ("SELECT anyUser FROM AnyUser anyUser WHERE anyUser.name = :username AND anyUser.pwhash = :pwHash");
		q.setParameter("username", username);
		try {
			q.setParameter("pwHash", Util.getMD5Checksum(password));
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		}
		
		try{
			AnyUser u = (AnyUser) q.getSingleResult();
			em.getTransaction().commit();
			return u;
		}
		catch(NoResultException e){
			e.printStackTrace();
			return null;
		}
		finally{
			em.close();
		}
	}

}

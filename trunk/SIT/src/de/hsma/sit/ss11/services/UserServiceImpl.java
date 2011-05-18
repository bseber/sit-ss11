package de.hsma.sit.ss11.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import de.hsma.sit.ss11.entities.AnyUser;

public class UserServiceImpl implements UserService {

	public EntityManagerFactory emf = Persistence.createEntityManagerFactory("SIT");
	
	@Override
	public AnyUser getUser(String name) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		Query q = em.createQuery ("SELECT anyUser FROM AnyUser anyUser WHERE anyUser.name = :username");
		q.setParameter("username", name);
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

	@SuppressWarnings("unchecked")
	@Override
	public List<AnyUser> getAllUser() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		Query q = em.createQuery ("SELECT anyUser FROM AnyUser anyUser");
		try{
			List<AnyUser> list = q.getResultList();
			return list;
		}
		catch(NoResultException e){
			return null;
		}
		finally{
			em.getTransaction().commit();
			em.close();
		}
	}

	@Override
	public void registerUser(String name, String password, String privatekey) {
		// TODO Auto-generated method stub
		
	}

}

package de.hsma.sit.ss11.services;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import de.hsma.sit.ss11.entities.AnyUser;
import de.hsma.sit.ss11.helper.Util;

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
			em.getTransaction().commit();
			return u;
		}
		catch(NoResultException e){
			return null;
		}
		finally{
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
			em.getTransaction().commit();
			return list;
		}
		catch(NoResultException e){
			return null;
		}
		finally{
			em.close();
		}
	}

	@Override
	public boolean registerUser(String username, String password, String pwToEncryptPrivateKey) {
		
		// Benutzer existiert noch nicht
		if ( getUser(username) == null ) {
			EntityManager em = null;
			try{
				AnyUser user = new AnyUser();
				user.setName(username);
				user.setPwhash(Util.getMD5Checksum(password));

				// der öffentliche und private Schlüssel des Benutzers wird generiert
				Cipher cipher = Cipher.getInstance("DES");
				cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(pwToEncryptPrivateKey.getBytes(), "DES"));
				KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
				KeyPair kp = kpg.generateKeyPair();
				user.setPublicKey(kp.getPublic().getEncoded());
				user.setEncryptedPrivateKey(cipher.doFinal(kp.getPrivate().getEncoded()));
				user.setPrivateKeyHash(Util.getMD5Checksum(new String(kp.getPrivate().getEncoded())));
				// entfernt das Keypair aus dem Speicher
				kp = null;
			
				em = emf.createEntityManager();
				em.getTransaction().begin();
			
				// speichert den Benutzer in der Datenbank
				em.persist(user);
				em.getTransaction().commit();
				return true;
			}
			catch ( Exception e) {
				e.printStackTrace();
				return false;
			}
			finally{
				if (em != null)
					em.close();
			}
		}
		else {
			return false;
		}
	}

}

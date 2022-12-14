package db.jpa;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;


import db.interfaces.UserManager;
import pojos.user.Role;
import pojos.user.User;

public class JPAUserManager implements UserManager {

	private static EntityManager em;

	@Override
	public void connect() {
		em = Persistence.createEntityManagerFactory("provider").createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
		em.getTransaction().commit();
	}

	@Override

	public void disconnect() {
		em.close();
	}

	@Override

	public void createUser(User user) {
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
	}

	@Override

	public void createRole(Role role) {
		em.getTransaction().begin();
		em.persist(role);
		em.getTransaction().commit();
	}

	@Override

	public Role getRole(int id) {
		Query q = em.createNativeQuery("SELECT * FROM roles WHERE id = ?", Role.class);
		q.setParameter(1, id);
		Role role = (Role) q.getSingleResult();
		return role;
	}

	public User getUser(String username) {
		Query q = em.createNativeQuery("SELECT * FROM users WHERE username = ?", Role.class);
		q.setParameter(1, username);
		User user = (User) q.getSingleResult();
		return user;
	}

	@Override

	public List<Role> getRoles() {
		Query q = em.createNativeQuery("SELECT * FROM  roles", Role.class);
		List<Role> roles = (List<Role>) q.getResultList();
		return roles;
	}

	@Override
	public User checkPassword(String username, String password) {
		User user = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			byte[] hash = md.digest();
			Query q = em.createNativeQuery("SELECT * FROM users WHERE username = ? AND password = ? ", User.class);
			q.setParameter(1, username);
			q.setParameter(2, hash);
			user = (User) q.getSingleResult();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoResultException noResult) {
			return null;
		}
		return user;
	}

	public void setUsername( User user, String newUsername) {
		em.getTransaction().begin();
		user.setUsername(newUsername);
		em.getTransaction().commit();
		em.close();
	}

public void deleteUser(User user) {
		em.getTransaction().begin();
		em.remove(user);
		em.getTransaction().commit();
		em.close();
}}
package org.jboss.community.pv243.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.TypedQuery;

import org.jboss.community.pv243.model.User;

@Stateless
@RequestScoped
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class UserManager {

	@Inject
	private EntityManager em;
	
	@Inject
	private Logger log;
	
	public void registerUser(User user) {
		try {
			em.getTransaction().begin();
			em.persist(user);
			em.getTransaction().commit();
			log.info("User: name=" + user.getName() + " " + user.getSecondName() 
					+ " was succesfully registered");
		} finally {
			em.close();
		}
	}
	
	public void deleteUser(User user) {
		try {
			em.getTransaction().begin();
			em.remove(user);
			em.getTransaction().commit();
			log.info("User: name=" + user.getName() + " " + user.getSecondName() 
					+ " was succesfully deleted");
		} finally {
			em.close();
		}
		
	}
	
	public void updateUser(int id, User newUser) {
		User user = em.find(User.class, id);
		String tempFirstName = user.getName();
		String tempSecondName = user.getSecondName();
		try{
			em.getTransaction().begin();
			user.setName(newUser.getName());
			user.setSecondName(newUser.getSecondName());
			em.getTransaction().commit();
			log.info("User: name=" + tempFirstName + " " + tempSecondName
				+ " was succesfully updated");
		} finally {
			em.close();
		}
	}
	
	public void updateUser(int id, String firstName, String secondName) {
		User user = em.find(User.class, id);
		String tempFirstName = user.getName();
		String tempSecondName = user.getSecondName();
		try{
			em.getTransaction().begin();
			user.setName(firstName);
			user.setSecondName(secondName);
			em.getTransaction().commit();
			log.info("User: name=" + tempFirstName + " " + tempSecondName
				+ " was succesfully updated");
		} finally {
			em.close();
		}
	}
	
	public User getUser(int id) {
		return em.find(User.class, id);
	}
	
	public List<User> getAllUsers() {
		TypedQuery<User> query = em.createNamedQuery("User.findAll", User.class);
		return query.getResultList();
	}
	
}

package org.jboss.community.pv243.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.jboss.community.pv243.model.User;

@Stateless
@RequestScoped
public class UserManager {

	@Inject
	private EntityManager em;

	@Inject
	private Logger log;

	// TODO: implement public User authUser(String name, String password) {}
	
	public void registerUser(User user) {
		em.persist(user);
		log.info("User: name=" + user.getFirstName() + " " + user.getSecondName()
				+ " was succesfully registered");
	}

	public void deleteUser(User user) {
		em.remove(em.merge(user));
		log.info("User: name=" + user.getFirstName() + " " + user.getSecondName()
				+ " was succesfully deleted");
	}

	public void updateUser(User newUser) {
		em.merge(newUser);
		log.info("User: " + newUser + " was succesfully updated");
	}

	public User getUser(int id) {
		return em.find(User.class, id);
	}

	public List<User> getAllUsers() {
		TypedQuery<User> query = em
				.createNamedQuery("User.findAll", User.class);
		return query.getResultList();
	}

}

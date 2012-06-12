package org.jboss.community.pv243.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.jboss.community.pv243.model.Reservation;
import org.jboss.community.pv243.model.User;


@Stateless
@RequestScoped
public class UserManager {

	@Inject
	private EntityManager em;

	@Inject
	private Logger log;
	
	@Inject
	private Event<User> userEventSrc;

	public User authUser(String email, String password) {
		// TODO: better (security) handling 
		TypedQuery<User> query = em
				.createNamedQuery("User.auth", User.class);
		query.setParameter("email", email);
		query.setParameter("password", password);
		return query.getSingleResult();
	}
	
	public void registerUser(User user) {
		if (user.getReservations() == null)
			user.setReservations(new ArrayList<Reservation>());
		em.persist(user);
		log.info("User: name=" + user.getFirstName() + " " + user.getSecondName()
		+ " was succesfully created");
		userEventSrc.fire(user);
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

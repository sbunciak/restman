package org.jboss.community.pv243.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.security.RolesAllowed;
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

	public User authUser(String email) {
		TypedQuery<User> query = em
				.createNamedQuery("User.auth", User.class);
		query.setParameter("email", email);
		return query.getSingleResult();
	}
	
	public void registerUser(User user) {
		if (user.getReservations() == null)
			user.setReservations(new ArrayList<Reservation>());
		user.setRole("USER");
		em.persist(user);
		log.info("User: name=" + user.getFirstName() + " " + user.getSecondName()
		+ " was succesfully created");
		userEventSrc.fire(user);
	}

	@RolesAllowed({"ADMIN"})
	public void deleteUser(User user) {
		em.remove(em.merge(user));
		log.info("User: name=" + user.getFirstName() + " " + user.getSecondName()
				+ " was succesfully deleted");
		userEventSrc.fire(user);
	}
	
	@RolesAllowed({"USER", "ADMIN"})
	public void updateUser(User newUser) {
		em.merge(newUser);
		log.info("User: " + newUser + " was succesfully updated");
		userEventSrc.fire(newUser);
	}

	@RolesAllowed({"ADMIN"})
	public User getUser(int id) {
		return em.find(User.class, id);
	}

	@RolesAllowed({"ADMIN"})
	public List<User> getAllUsers() {
		TypedQuery<User> query = em
				.createNamedQuery("User.findAll", User.class);
		return query.getResultList();
	}

}

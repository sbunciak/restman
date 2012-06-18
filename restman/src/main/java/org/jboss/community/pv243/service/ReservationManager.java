package org.jboss.community.pv243.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.community.pv243.model.MenuItem;
import org.jboss.community.pv243.model.Reservation;
import org.jboss.community.pv243.model.Restaurant;
import org.jboss.community.pv243.model.User;

@Stateless
@RequestScoped
public class ReservationManager {

	@Inject
	private EntityManager em;

	@Inject
	private Logger log;

	@RolesAllowed({"USER", "MANAGER"})
	public void createReservation(Reservation reservation, User user, Restaurant restaurant) {
		
		user.getReservations().add(reservation);
		em.merge(user);
		restaurant.getReservations().add(reservation);
		em.merge(restaurant);
		
		reservation.setUser(user);
		reservation.setRestaurant(restaurant);
		
		if (reservation.getReservedMenu() == null) 
			reservation.setReservedMenu(new ArrayList<MenuItem>());
		em.persist(reservation);
		log.info("Reservation: " + reservation.getId() 
				+ " was created for user "
				+ user.getFirstName() + " " + user.getSecondName());

	}

	@RolesAllowed({"USER", "MANAGER"})
	public void removeReservation(Reservation reservation) {
		Reservation attachedReserv = em.merge(reservation);
		attachedReserv.getUser().getReservations().remove(attachedReserv);
		attachedReserv.getRestaurant().getReservations().remove(attachedReserv);
		em.remove(attachedReserv);
		log.info("Reservation: " + reservation.getId() 
				+ " was removed");

	}
	
	@RolesAllowed({"USER", "MANAGER"})
	public void updateReservation(Reservation reservation) {
		em.merge(reservation);
		log.info("Reservation: " + reservation.getId() 
				+ " was updated for user: name="
				+ reservation.getUser().getFirstName()
				+ " " + reservation.getUser().getSecondName());
	}

	@RolesAllowed({"USER", "MANAGER"})
	public void removeAllUserReservations(User user) {
		for (Reservation reservation : user.getReservations()) {
			removeReservation(reservation);
		}
		log.info("All reservation were removed from user: name="
				+ user.getFirstName() + " " + user.getSecondName());
	}
	
	@RolesAllowed({"MANAGER"})
	public void removeAllRestaurantReservation(Restaurant restaurant) {
		for (Reservation reservation : restaurant.getReservations()) {
			removeReservation(reservation);
		}
		log.info("All reservation were removed from restaurant: name="
				+ restaurant.getName());
	}

	@RolesAllowed({"USER", "MANAGER"})
	public Reservation getReservation(int idReservation) {
		return em.find(Reservation.class, idReservation);
	}

	@RolesAllowed({"USER", "MANAGER"})
	public Collection<Reservation> getUserReservations(User user) {
		return em.merge(user).getReservations();
	}
	
	@RolesAllowed({"MANAGER"})
	public Collection<Reservation> getRestaurantReservations(
			Restaurant restaurant) {
		return em.merge(restaurant).getReservations();
	}
	
	@RolesAllowed({"MANAGER"})
	public Collection<Reservation> getMenuItemReservations(
			MenuItem menuItem) {
		return em.merge(menuItem).getReservations();
	}

}

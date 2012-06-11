package org.jboss.community.pv243.service;

import java.util.Collection;
import java.util.logging.Logger;

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

	public void createReservation(Reservation reservation) {
		em.persist(reservation);
		log.info("Reservation: " + reservation.getId() 
				+ " was created for user: name="
				+ reservation.getUser().getFirstName()
				+ " " + reservation.getUser().getSecondName());

	}

	public void removeReservation(Reservation reservation) {
		Reservation attachedReserv = em.merge(reservation);
		attachedReserv.getUser().getReservations().remove(attachedReserv);
		attachedReserv.getRestaurant().getReservations().remove(attachedReserv);
		em.remove(attachedReserv);
		log.info("Reservation: " + reservation.getId() 
				+ " was removed");

	}
	
	public void updateReservation(Reservation reservation) {
		em.merge(reservation);
		log.info("Reservation: " + reservation.getId() 
				+ " was updated for user: name="
				+ reservation.getUser().getFirstName()
				+ " " + reservation.getUser().getSecondName());
	}

	public void removeAllUserReservations(User user) {
		for (Reservation reservation : user.getReservations()) {
			removeReservation(reservation);
		}
		log.info("All reservation were removed from user: name="
				+ user.getFirstName() + " " + user.getSecondName());
	}
	
	public void removeAllRestaurantReservation(Restaurant restaurant) {
		for (Reservation reservation : restaurant.getReservations()) {
			removeReservation(reservation);
		}
		log.info("All reservation were removed from restaurant: name="
				+ restaurant.getName());
	}

	public Reservation getReservation(int idReservation) {
		return em.find(Reservation.class, idReservation);
	}

	public Collection<Reservation> getUserReservations(User user) {
		return em.merge(user).getReservations();
	}
	
	public Collection<Reservation> getRestaurantReservations(
			Restaurant restaurant) {
		return em.merge(restaurant).getReservations();
	}
	
	public Collection<Reservation> getMenuItemReservations(
			MenuItem menuItem) {
		return em.merge(menuItem).getReservations();
	}

}

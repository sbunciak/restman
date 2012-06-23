package org.jboss.community.pv243.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.community.pv243.model.Reservation;
import org.jboss.community.pv243.model.Restaurant;
import org.jboss.community.pv243.model.User;
import org.jboss.community.pv243.service.ReservationManager;
import org.jboss.community.pv243.service.RestaurantManager;

@Named
@SessionScoped
public class ReservationController implements Serializable {

	private static final long serialVersionUID = -2801739162098295137L;

	@Inject
	FacesContext facesContext;

	@Inject
	ReservationManager reservationManager;
	
	@Inject 
	RestaurantManager restaurantManager;

	private Reservation newReservation;
	
	private boolean edit = false;

	@Produces
	@Named
	public Reservation getNewReservation(){
		return newReservation;
	}
	
	public void registerReservation(User u) {

		Restaurant restaurant = restaurantManager.getRestaurant(3) ;
		reservationManager.createReservation(newReservation, u, restaurant);
		initReservation();
	}
	
	public void deleteReservation(Reservation reservation) {
		reservationManager.removeReservation(reservation);
		initReservation();
	}
	
	public void editReservation(Reservation reservation) {
		newReservation = reservationManager.getReservation((reservation.getId()));
		edit = true;
	}
	
	public void save() {
		reservationManager.updateReservation(newReservation);
		edit = false;
		initReservation();
	}
	
	public void clear() {
		edit = false;
		initReservation();
	}

	public boolean isEdit() {
		return edit;
	}

	@PostConstruct
	public void initReservation() {
		newReservation = new Reservation();
	}

}

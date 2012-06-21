package org.jboss.community.pv243.controller;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.community.pv243.model.Reservation;
import org.jboss.community.pv243.model.Restaurant;
import org.jboss.community.pv243.model.User;
import org.jboss.community.pv243.service.ReservationManager;

@Named
@SessionScoped
public class ReservationController implements Serializable {

	private static final long serialVersionUID = -2801739162098295137L;

	@Inject
	FacesContext facesContext;

	@Inject
	ReservationManager reservationManager;

	private Reservation newReservation;

	private boolean edit = false;

	@Produces
	@Named
	public Reservation getReservation() {
		return newReservation;
	}

	public void clear() {
		edit = false;
		initReservation();
	}

	public boolean isEdit() {
		return edit;
	}

	public void registerReservation(User u, Restaurant r) {
		reservationManager.createReservation(newReservation, u ,r);
		facesContext.addMessage(null, new FacesMessage(
				FacesMessage.SEVERITY_INFO, "Registration successful",
				"Reservation was successfuly registered"));
		
		initReservation();
	}

	public void editReservation(Reservation newReservation) {
		newReservation = reservationManager.getReservation((newReservation.getId()));
		edit = true;
	}

	public void save() {
		reservationManager.updateReservation(newReservation);
		edit = false;
		initReservation();
	}

	@PostConstruct
	public void initReservation() {
		newReservation = new Reservation();
	}

	public Collection<Reservation> getUserReservations(User u) {
		return reservationManager.getUserReservations(u);
	}

	public void updateReservation(Reservation r) {
		reservationManager.updateReservation(r);
	}

}

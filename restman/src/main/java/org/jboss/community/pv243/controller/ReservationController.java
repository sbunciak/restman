package org.jboss.community.pv243.controller;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.community.pv243.model.Reservation;
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
	
	public Collection<Reservation> getUserReservations(User u) {
		return reservationManager.getUserReservations(u);
	}
	
	public void editReservation(Reservation r) {
		reservationManager.updateReservation(r);
	}
	
	public void deleteReservation(Reservation r) {
		reservationManager.removeReservation(r);
	}
}

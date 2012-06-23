package org.jboss.community.pv243.controller;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.community.pv243.model.Reservation;
import org.jboss.community.pv243.model.Restaurant;
import org.jboss.community.pv243.model.User;
import org.jboss.community.pv243.service.ReservationManager;
import org.jboss.community.pv243.service.RestaurantManager;
import org.jboss.community.pv243.service.UserManager;

@Named
@SessionScoped
//@FacesConverter(value="userConverter", forClass = User.class)
public class ReservationController implements Serializable, Converter{

	private static final long serialVersionUID = -2801739162098295137L;

	@Inject
	FacesContext facesContext;

	@Inject
	ReservationManager reservationManager;
	
	@Inject
	RestaurantManager restaurantManager;

	private Reservation newReservation;
	
	private Restaurant restaurant;

	private boolean edit = false;
	
	@Inject
	UserManager userManager;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		int id = Integer.valueOf(value);
		return userManager.getUser(id);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		return String.valueOf(((User)value).getId());
	}

	public Restaurant getLoggedRestaurant(){
		if (!facesContext.getExternalContext().isUserInRole("MANAGER")){
			throw new IllegalStateException("Uzivatel neni v roli MANAGER");
		}
		if (restaurant== null){
		restaurant = restaurantManager.getRestaurantByEmail(facesContext.getExternalContext().getUserPrincipal().getName());
		}
		return restaurant;
	}
	
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

	public void registerReservation() {
		newReservation.setRestaurant(getLoggedRestaurant());
		reservationManager.createReservation(newReservation);
		facesContext.addMessage(null, new FacesMessage(
				FacesMessage.SEVERITY_INFO, "Registration successful",
				"Reservation was successfuly registered"));
		
		initReservation();
	}
	
	public void deleteReservation(Reservation reservation){
		reservationManager.removeReservation(reservation);
		initReservation();
	}

	public void editReservation(Reservation newReservation) {
		this.newReservation = reservationManager.getReservation((newReservation.getId()));
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

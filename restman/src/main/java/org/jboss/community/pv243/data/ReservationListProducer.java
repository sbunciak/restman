package org.jboss.community.pv243.data;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.community.pv243.model.Reservation;
import org.jboss.community.pv243.model.Restaurant;
import org.jboss.community.pv243.service.ReservationManager;
import org.jboss.community.pv243.service.RestaurantManager;


@ManagedBean
@RequestScoped
public class ReservationListProducer {
	
	@Inject
	ReservationManager reservationManager;
	
	@Inject
	RestaurantManager restaurantManager;
	
	@Inject
	FacesContext facesContext;
	
	private Collection<Reservation> reservations;
	
	
	private Restaurant restaurant;

	
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
	public Collection<Reservation> getReservationItems(){
		return reservations;
	}
	
	@PostConstruct
	public void initiateReservationItems(){
		reservations = reservationManager.getRestaurantReservations(getLoggedRestaurant());
	}

}

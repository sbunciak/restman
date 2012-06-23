package org.jboss.community.pv243.data;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.community.pv243.model.Reservation;
import org.jboss.community.pv243.model.Restaurant;
import org.jboss.community.pv243.service.ReservationManager;
import org.jboss.community.pv243.service.RestaurantManager;


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
	
	public void onReservationsListChanged(@Observes(notifyObserver=Reception.IF_EXISTS) final Reservation reservation){
		initiateReservationItems();
	}

}

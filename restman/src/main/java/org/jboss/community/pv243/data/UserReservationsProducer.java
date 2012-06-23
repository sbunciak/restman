package org.jboss.community.pv243.data;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.community.pv243.model.Reservation;
import org.jboss.community.pv243.model.User;
import org.jboss.community.pv243.service.UserManager;

@RequestScoped
public class UserReservationsProducer {
	
	@Inject
	UserManager userManager;
	
	@Inject
	FacesContext facesContext;
	
	private User user;
	
	private Collection<Reservation> userReservations;
	
	public User getLoggedUser(){
		if (!facesContext.getExternalContext().isUserInRole("USER")){
			throw new IllegalStateException("Uzivatel neni v roli USER");
		}
		if (user == null){
		user = userManager.getUserByEmail(facesContext.getExternalContext().getUserPrincipal().getName());
		}
		return user;
	}
	
	@Produces
	@Named
	public Collection<Reservation> getUserReservations() {
		return userReservations;
	}
	
	@PostConstruct
	public void initiateUserReservations(){
		userReservations = getLoggedUser().getReservations();
	}
	
//	public void onUserReservationChanged(@Observes(
//			notifyObserver = Reception.IF_EXISTS) final Reservation reservation){
//		initiateUserReservations();
//	}
	
}

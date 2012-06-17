package org.jboss.community.pv243.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.community.pv243.model.Restaurant;
import org.jboss.community.pv243.service.RestaurantManager;

@Model
public class RestaurantController {
	@Inject
	FacesContext facesContext;
	
	@Inject
	RestaurantManager restaurantManager;
	
	private Restaurant restaurant;
	
	@Produces
	@Named
	public Restaurant getRestaurant(){
		return restaurant;
	}
	
	public void registerRestaurant(){
		restaurantManager.createRestaurant(restaurant);
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registration successful", "Restauration was successfuly registered"));
		restaurant = new Restaurant();
	}
	
	
	@PostConstruct
	public void initRestauratn(){
		restaurant = new Restaurant();
	}
	
}

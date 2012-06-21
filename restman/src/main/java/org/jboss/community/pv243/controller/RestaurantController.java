package org.jboss.community.pv243.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.community.pv243.model.Restaurant;
import org.jboss.community.pv243.service.RestaurantManager;

@Named
@SessionScoped
public class RestaurantController implements Serializable {

	private static final long serialVersionUID = 8049789937694766621L;

	@Inject
	FacesContext facesContext;
	
	@Inject
	private RestaurantManager restaurantManager;
	
	private Restaurant newRestaurant;
	
	private boolean edit = false;
	
	@Produces
	@Named
	public Restaurant getRestaurant(){
		return newRestaurant;
	}
	
	public void registerRestaurant(){
		restaurantManager.createRestaurant(newRestaurant);
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
				"Registration successful", "Restauration was successfuly registered"));
		newRestaurant = new Restaurant();
		initRestaurant();
	}
	
	public void deleteRestaurant(Restaurant newRestaurant){
		restaurantManager.deleteRestaurant(newRestaurant);
		initRestaurant();
	}
	
	public void editRestaurant(Restaurant restaurant){
		newRestaurant = restaurantManager.getRestaurant((restaurant.getId()));
		edit = true;
	}
	
	public void save() {
		restaurantManager.updateRestaurant(newRestaurant);
		edit = false;
		initRestaurant();
	}
	
	public void clear() {
		edit = false;
		initRestaurant();
	}
	
	public boolean isEdit() {
		return edit;
	}
	
	@PostConstruct
	public void initRestaurant(){
		newRestaurant = new Restaurant();
	}
	
	public List<Restaurant> getAllRestaurants() {
		return restaurantManager.getAllRestaurants();
	}
}

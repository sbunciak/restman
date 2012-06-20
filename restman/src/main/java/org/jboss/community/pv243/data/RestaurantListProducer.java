package org.jboss.community.pv243.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.community.pv243.model.Restaurant;
import org.jboss.community.pv243.service.RestaurantManager;

@RequestScoped
public class RestaurantListProducer {

	@Inject
	RestaurantManager restaurantManager;
	
	private List<Restaurant> restaurants;
	
	@Produces
	@Named
	public List<Restaurant> getRestaurants(){
		return restaurants;
	}
	
	@PostConstruct
	public void initiateRestaurants(){
		restaurants = restaurantManager.getAllRestaurants();
	}
	
	public void onRestaurantListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Restaurant restaurant){
		initiateRestaurants();
	}
	
}

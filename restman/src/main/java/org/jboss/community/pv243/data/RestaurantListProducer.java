package org.jboss.community.pv243.data;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.community.pv243.model.Restaurant;
import org.jboss.community.pv243.service.RestaurantManager;

public class RestaurantListProducer {

	@Inject
	RestaurantManager restaurantManager;
	
	private Collection<Restaurant> restaurants;
	
	@Produces
	@Named
	public Collection<Restaurant> getRestaurants(){
		return restaurants;
	}
	
	@PostConstruct
	public void initiateRestaurants(){
		restaurants = restaurantManager.getAllRestaurants();
	}
	
}

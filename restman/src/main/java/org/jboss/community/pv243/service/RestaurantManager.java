package org.jboss.community.pv243.service;

import java.util.Collection;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.jboss.community.pv243.model.Restaurant;

@Stateless
@RequestScoped
public class RestaurantManager {

	@Inject
	private EntityManager em;

	@Inject
	private Logger log;

	// TODO: implement public Restaurant getReservationsOfRestaurant(Restaurant r) {}
	
	public void createRestaurant(Restaurant restaurant) {
		em.persist(restaurant);
		log.info("Restaurant: " + restaurant.getName()
				+ " was succesfully created");
	}

	public void deleteRestaurant(Restaurant restaurant) {
		em.remove(em.merge(restaurant));
		log.info("Restaurant: " + restaurant.getName()
				+ " was succesfully deleted");
	}

	public void updateRestaurant(Restaurant restaurant) {
		em.merge(restaurant);
		log.info("Restaurant: " + 
				restaurant.getName() + " was succesfully updated");
	}

	public Restaurant getRestaurant(int id) {
		return em.find(Restaurant.class, id);
	}

	public Collection<Restaurant> getAllRestaurants() {
		TypedQuery<Restaurant> query = em.createNamedQuery(
				"Restaurant.findAll", Restaurant.class);
		return query.getResultList();
	}

}

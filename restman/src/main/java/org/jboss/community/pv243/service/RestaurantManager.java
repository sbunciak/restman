package org.jboss.community.pv243.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.jboss.community.pv243.model.MenuItem;
import org.jboss.community.pv243.model.Reservation;
import org.jboss.community.pv243.model.Restaurant;

@Stateless
@RequestScoped
public class RestaurantManager {

	@Inject
	private EntityManager em;

	@Inject
	private Logger log;
	
	@Inject
	private Event<Restaurant> restaurantEventSrc;

	public Restaurant authRestaurant(String email, String password) {
		// TODO: better (security) handling
		TypedQuery<Restaurant> query = em.createNamedQuery("Restaurant.auth",
				Restaurant.class);
		query.setParameter("email", email);
		query.setParameter("password", password);
		return query.getSingleResult();
	}

	public void createRestaurant(Restaurant restaurant) {
		if (restaurant.getReservations() == null) 
			restaurant.setReservations(new ArrayList<Reservation>());
		if (restaurant.getMenu() == null) {
			restaurant.setMenu(new ArrayList<MenuItem>());
		}
		em.persist(restaurant);
		log.info("Restaurant: " + restaurant.getName()
				+ " was succesfully created");
		restaurantEventSrc.fire(restaurant);
	}

	public void deleteRestaurant(Restaurant restaurant) {
		em.remove(em.merge(restaurant));
		log.info("Restaurant: " + restaurant.getName()
				+ " was succesfully deleted");
		restaurantEventSrc.fire(restaurant);
	}

	public void updateRestaurant(Restaurant restaurant) {
		em.merge(restaurant);
		log.info("Restaurant: " + restaurant.getName()
				+ " was succesfully updated");
		restaurantEventSrc.fire(restaurant);
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

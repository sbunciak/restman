package org.jboss.community.pv243.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.security.RolesAllowed;
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
	private ReservationManager rm;

	@Inject
	private Logger log;
	
	@Inject
	private Event<Restaurant> restaurantEventSrc;

	public Restaurant authRestaurant(String email, String password) {
		TypedQuery<Restaurant> query = em.createNamedQuery("Restaurant.auth",
				Restaurant.class);
		query.setParameter("email", email);
		query.setParameter("password", password);
		return query.getSingleResult();
	}

	@RolesAllowed({"ADMIN"})
	public void createRestaurant(Restaurant restaurant) {
		if (restaurant.getReservations() == null) 
			restaurant.setReservations(new ArrayList<Reservation>());
		if (restaurant.getMenu() == null) {
			restaurant.setMenu(new ArrayList<MenuItem>());
		}
		restaurant.setRole("MANAGER");
		em.persist(restaurant);
		log.info("Restaurant: " + restaurant.getName()
				+ " was succesfully created");
		restaurantEventSrc.fire(restaurant);
	}

	@RolesAllowed({"ADMIN"})
	public void deleteRestaurant(Restaurant restaurant) {
		rm.removeAllRestaurantReservation(restaurant);
		em.remove(em.merge(restaurant));
		log.info("Restaurant: " + restaurant.getName()
				+ " was succesfully deleted");
		restaurantEventSrc.fire(restaurant);
	}

	@RolesAllowed({"MANAGER", "ADMIN"})
	public void updateRestaurant(Restaurant restaurant) {
		em.merge(restaurant);
		log.info("Restaurant: " + restaurant.getName()
				+ " was succesfully updated");
		restaurantEventSrc.fire(restaurant);
	}

	public Restaurant getRestaurant(int id) {
		return em.find(Restaurant.class, id);
	}
	
	public Restaurant getRestaurantByEmail(String email){
		TypedQuery<Restaurant> query = em.createNamedQuery("Restaurant.getByEmail", Restaurant.class);
		query.setParameter("email", email);
		return query.getSingleResult();
	}
	
	public Restaurant getRestaurantByName(String name){
		TypedQuery<Restaurant> query = em.createNamedQuery("Restaurant.getByName", Restaurant.class);
		query.setParameter("name", name);
		return query.getSingleResult();
	}

	public List<Restaurant> getAllRestaurants() {
		TypedQuery<Restaurant> query = em.createNamedQuery(
				"Restaurant.findAll", Restaurant.class);
		return query.getResultList();
	}

}

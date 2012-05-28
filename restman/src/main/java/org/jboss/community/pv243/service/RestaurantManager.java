package org.jboss.community.pv243.service;

import java.util.Collection;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.jboss.community.pv243.model.MenuItem;
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
		em.remove(em.find(Restaurant.class, restaurant.getId()));

		log.info("Restaurant: " + restaurant.getName()
				+ " was succesfully deleted");
	}

	public void updateRestaurant(Restaurant newRestaurant) {
		em.merge(newRestaurant);
		
		log.info("Restaurant: " + newRestaurant + " was succesfully updated");
	}

	public void updateRestaurant(int id_r, String name, String information,
			Collection<MenuItem> menu) {
		Restaurant restaurant = em.find(Restaurant.class, id_r);
		String restName = restaurant.getName();

		restaurant.setName(name);
		restaurant.setInformation(information);
		restaurant.setMenu(menu);

		em.merge(restaurant);
		
		log.info("Restaurant: " + restName + " was succesfully updated");
	}

	public Restaurant getRestaurant(int id) {
		return em.find(Restaurant.class, id);
	}

	public Collection<Restaurant> getAllRestaurants() {
		TypedQuery<Restaurant> query = em.createNamedQuery(
				"Restaurant.findAll", Restaurant.class);
		return query.getResultList();
	}

	public Collection<MenuItem> getMenuOfRestaurant(int id) {
		return em.find(Restaurant.class, id).getMenu();
	}

	public MenuItem getMenuItemOfRestaurant(int id_r, int id_mi) {
		Restaurant restaurant = em.find(Restaurant.class, id_r);

		for (MenuItem it : restaurant.getMenu()) {
			if (it.getId() == id_mi)
				return it;
		}

		return null;
	}

}

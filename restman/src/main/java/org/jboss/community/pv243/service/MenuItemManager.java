package org.jboss.community.pv243.service;

import java.util.Collection;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.community.pv243.model.MenuItem;
import org.jboss.community.pv243.model.Reservation;
import org.jboss.community.pv243.model.Restaurant;

@Stateless
@RequestScoped
public class MenuItemManager {

	@Inject
	private EntityManager em;

	@Inject
	private Logger log;

	public void createMenuItem(MenuItem menuItem) {
		em.persist(menuItem);
		log.info("New menu item: " + menuItem.getName() 
				+ " was created");
	}
	
	public void updateMenuItem(MenuItem menuItem) {
		em.merge(menuItem);
		log.info("Menu item: " + menuItem.getName() 
				+ " was edited");
	}
	
	public void removeMenuItem(MenuItem menuItem) {
		em.remove(em.merge(menuItem));
		log.info("Menu item: " + menuItem.getName() 
				+ " was removed");
	}
	
	public MenuItem getMenuItem(int idMenuItem) {
		return em.find(MenuItem.class, idMenuItem);
	}
	
	public Collection<MenuItem> getRestaurantMenu(Restaurant restaurant) {
		return em.merge(restaurant).getMenu();
	}
	
	public Collection<MenuItem> getReservationMenu(Reservation reservation) {
		return em.merge(reservation).getReservedMenu();
	}
	
}

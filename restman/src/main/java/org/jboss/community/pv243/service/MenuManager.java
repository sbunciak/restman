package org.jboss.community.pv243.service;

import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.community.pv243.model.MenuItem;
import org.jboss.community.pv243.model.Restaurant;

@Stateless
@RequestScoped
public class MenuManager {

	@Inject
	private EntityManager em;
	
	@Inject
	private Logger log;
	
	public void addMenuItemsToMenu(int id, Collection<MenuItem> menuItems) {
		Restaurant restaurant = em.find(Restaurant.class, id);
		try {
			em.getTransaction().begin();
			restaurant.getMenu().addAll(menuItems);
			em.getTransaction().commit();
			log.info("Menu items were added to menu of restaurant "
				+ restaurant.getName());
		} finally {
			em.close();
		}
	}
	
	public void createNewMenu(int id, Collection<MenuItem> menu) {
		Restaurant restaurant = em.find(Restaurant.class, id);
		try {
			em.getTransaction().begin();
			restaurant.setMenu(menu);
			em.getTransaction().commit();
			log.info("Restaurant " + restaurant.getName() + " was set a new menu");
		} finally {
			em.close();
		}
	}
	
	public void removeWholeMenu(int id) {
		Restaurant restaurant = em.find(Restaurant.class, id);
		try {
			em.getTransaction().begin();
			restaurant.getMenu().clear();
			em.getTransaction().commit();
			log.info("Menu of restaurant " + restaurant.getName() + " was cleared");
		} finally {
			em.close();
		}
	}
	
	public void removeMenuItemsFromMenu(int id, Collection<MenuItem> menuItems) {
		Restaurant restaurant = em.find(Restaurant.class, id);
		try {
			em.getTransaction().begin();
			restaurant.getMenu().removeAll(menuItems);
			em.getTransaction().commit();
			log.info("Some menu items from menu of restaurant "
				+ restaurant.getName() + " were removed");
		} finally {
			em.close();
		}
	}
	
	public void updateMenuItemInMenu(int id_r, int id_mi, MenuItem newMenuItem) {
		Restaurant restaurant = em.find(Restaurant.class, id_r);
		Iterator<MenuItem> it = restaurant.getMenu().iterator();
		MenuItem oldMenuItem = null;
		while (it.hasNext()) {
			MenuItem tempMenuItem = it.next();
			if (tempMenuItem.getId() == id_mi) {
				oldMenuItem = tempMenuItem;
				break;
			}
		}
		try {
			em.getTransaction().begin();
			oldMenuItem.setName(newMenuItem.getName());
			oldMenuItem.setPrize(newMenuItem.getPrize());
			oldMenuItem.setWeight(newMenuItem.getWeight());
			em.getTransaction().commit();
			log.info("Menu item " + newMenuItem.getName() + 
				" was updated in menu of restaurant " + restaurant.getName());
		} finally {
			em.close();
		}
	}
	
	public void updateMenuItemInMenu(int id_r, int id_mi, String name, int prize, int weight) {
		Restaurant restaurant = em.find(Restaurant.class, id_r);
		Iterator<MenuItem> it = restaurant.getMenu().iterator();
		MenuItem oldMenuItem = null;
		while (it.hasNext()) {
			MenuItem tempMenuItem = it.next();
			if (tempMenuItem.getId() == id_mi) {
				oldMenuItem = tempMenuItem;
				break;
			}
		}
		try {
			em.getTransaction().begin();
			oldMenuItem.setName(name);
			oldMenuItem.setPrize(prize);
			oldMenuItem.setWeight(weight);
			em.getTransaction().commit();
			log.info("Menu item " + name + 
				" was updated in menu of restaurant " + restaurant.getName());
		} finally {
			em.close();
		}
	}
	
}

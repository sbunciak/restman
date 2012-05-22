package org.jboss.community.pv243.service;

import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.community.pv243.model.MenuItem;
import org.jboss.community.pv243.model.Restaurant;

@Stateless
@RequestScoped
@Path("/restaurants")
@NamedQuery(name="Restaurant.findAll", query="SELECT r FROM Restaurant r")
public class RestaurantManager {

	@Inject
	private EntityManager em;
	
	@Inject
	private Logger log;
	
	public void createRestaurant(Restaurant restaurant) {
		try {
			em.getTransaction().begin();
			em.persist(restaurant);
			em.getTransaction().commit();
			log.info("Restaurant: " + restaurant.getName() 
					+ " was succesfully created");
		} finally {
			em.close();
		}
	}
	
	public void deleteRestaurant(Restaurant restaurant) {
		try {
			em.getTransaction().begin();
			em.remove(restaurant);
			em.getTransaction().commit();
			log.info("Restaurant: " + restaurant.getName() 
					+ " was succesfully deleted");
		} finally {
			em.close();
		}
		
	}
	
	public void updateRestaurant(int id_r, Restaurant newRestaurant) {
		Restaurant restaurant = em.find(Restaurant.class, id_r);
		String tempName = restaurant.getName();
		try{
			em.getTransaction().begin();
			restaurant.setName(newRestaurant.getName());
			restaurant.setInformation(newRestaurant.getInformation());
			restaurant.setMenu(newRestaurant.getMenu());
			em.getTransaction().commit();
			log.info("Restaurant: " + tempName
				+ " was succesfully updated");
		} finally {
			em.close();
		}
	}
	
	public void updateRestaurant(int id_r, String name, String information, 
			Collection<MenuItem> menu) {
		Restaurant restaurant = em.find(Restaurant.class, id_r);
		String restName = restaurant.getName();
		try{
			em.getTransaction().begin();
			restaurant.setName(name);
			restaurant.setInformation(information);
			restaurant.setMenu(menu);
			em.getTransaction().commit();
			log.info("Restaurant: " + restName
				+ " was succesfully updated");
		} finally {
			em.close();
		}
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.TEXT_XML)
	public Restaurant getRestaurant(@PathParam("id") int id) {
		return em.find(Restaurant.class, id);
	}
	
	@GET
	@Produces(MediaType.TEXT_XML)
	public Collection<Restaurant> getAllRestaurants() {
		TypedQuery<Restaurant> query = em.createNamedQuery("Restaurant.findAll", Restaurant.class);
		return query.getResultList();
	}
	
	@GET
	@Path("/{id}/menu")
	@Produces(MediaType.TEXT_XML)
	public Collection<MenuItem> getMenuOfRestaurant(@PathParam("id") int id) {
		return em.find(Restaurant.class, id).getMenu();
	}
	
	@GET
	@Path("/{id_r}/menu/{id_mi}")
	@Produces(MediaType.TEXT_XML)
	public MenuItem getMenuItemOfRestaurant(@PathParam("id_r") int id_r, @PathParam("id_mi") int id_mi) {
		Restaurant restaurant = em.find(Restaurant.class, id_r);
		Iterator<MenuItem> it = restaurant.getMenu().iterator();
		while (it.hasNext()) {
			MenuItem temp = it.next();
			if (temp.getId() == id_mi) return temp;
		}
		return null;
		
	}
	
}

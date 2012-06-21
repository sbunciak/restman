package org.jboss.community.pv243.rest;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.community.pv243.model.MenuItem;
import org.jboss.community.pv243.model.Reservation;
import org.jboss.community.pv243.model.Restaurant;
import org.jboss.community.pv243.service.RestaurantManager;

/**
 * Public REST interface for viewing restaurant info. Usage:
 * <ul>
 * <li>GET /restaurants/</li>
 * <li>GET /restaurants/{id} returns info about restaurant specified by the id</li>
 * <li>GET /restaurants/{id}/menu returns current menu of the restaurant</li>
 * <li>GET /restaurants/{id}/reservations/ returns reservation for restaurant</li>
 * </ul>
 * 
 * @author sbunciak
 * 
 */
@Stateless
@Path("/restaurants")
public class RestaurantManagerREST {

	@Inject
	RestaurantManager manager;

	@GET
	@Path("/{id}")
	@Produces(MediaType.TEXT_XML)
	public Restaurant getRestaurant(@PathParam("id") int id) {
		return manager.getRestaurant(id);
	}

	@GET
	@Produces(MediaType.TEXT_XML)
	public Collection<Restaurant> getAllRestaurants() {
		return manager.getAllRestaurants();
	}

	@GET
	@Path("/{id}/menu")
	@Produces(MediaType.TEXT_XML)
	public Collection<MenuItem> getMenuOfRestaurant(@PathParam("id") int id) {
		return manager.getRestaurant(id).getMenu();
	}

	@GET
	@Path("/{id}/reservations")
	@Produces(MediaType.TEXT_XML)
	public Collection<Reservation> getReservationsOfRestaurant(
			@PathParam("id") int id) {
		return manager.getRestaurant(id).getReservations();
	}

}
package org.jboss.community.pv243.rest;

import java.util.Collection;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.community.pv243.model.Reservation;
import org.jboss.community.pv243.model.Restaurant;
import org.jboss.community.pv243.model.User;
import org.jboss.community.pv243.service.ReservationManager;
import org.jboss.community.pv243.service.RestaurantManager;
import org.jboss.community.pv243.service.UserManager;

/**
 * Public REST interface for manipulating with reservations. Usage:
 * <ul>
 * <li>PUT /reservations/ creates a new reservation</li>
 * <li>POST /reservations/ modifies an already created reservation</li>
 * <li>DELETE /reservations/{id} removes reservation specified by id from
 * database</li>
 * <li>DELETE /reservations/user/{id} removes all reservations for the user
 * specified by id</li>
 * <li>GET /reservations/{id}/reservations returns reservation for restaurant</li>
 * <li>GET /reservations/user/{id} returns all reservation for the user
 * specified by id</li>
 * <li>GET /reservations/restaurant/{id} returns all reservation for the
 * restaurant specified by id</li>
 * </ul>
 * 
 * @author sbunciak
 * 
 */
@Stateless
@Path("/reservations")
public class ReservationManagerREST {

	@Inject
	ReservationManager manager;

	@Inject
	UserManager userManager;

	@Inject
	RestaurantManager restaurantManager;

	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public void createReservation(Restaurant restaurant, User user,
			Reservation reservation) {
		manager.createReservation(reservation, user, restaurant);
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public void updateReservation(Reservation r) {
		manager.updateReservation(r);
	}

	@DELETE
	@Path("/{id}")
	public void removeReservation(@PathParam("id") int reservation_id) {
		manager.removeReservation(manager.getReservation(reservation_id));
	}

	@DELETE
	@Path("/user/{id}")
	public void removeAllUserReservations(@PathParam("id") int user_id) {
		manager.removeAllUserReservations(userManager.getUser(user_id));
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_XML)
	@PermitAll
	public Reservation getReservation(@PathParam("id") int id) {
		return manager.getReservation(id);
	}

	@GET
	@Path("/user/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public Collection<Reservation> getUserReservations(
			@PathParam("id") int user_id) {
		return manager.getUserReservations(userManager.getUser(user_id));
	}

	@GET
	@Path("/restaurant/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public Collection<Reservation> getRestaurantReservations(
			@PathParam("id") int id) {
		return restaurantManager.getRestaurant(id).getReservations();
	}
}

package org.jboss.community.pv243.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.community.pv243.model.MenuItem;
import org.jboss.community.pv243.model.Reservation;
import org.jboss.community.pv243.model.Restaurant;
import org.jboss.community.pv243.model.User;
import org.jboss.community.pv243.service.MenuItemManager;
import org.jboss.community.pv243.service.ReservationManager;
import org.jboss.community.pv243.service.RestaurantManager;
import org.jboss.community.pv243.service.UserManager;
import org.jboss.community.pv243.util.Resources;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ReservationManagerTest {

	@Inject
	ReservationManager reservationManager;
	@Inject
	UserManager userManager;
	@Inject
	RestaurantManager restaurantManager;
	@Inject
	MenuItemManager menuManager;

	@Test
	public void createReservationTest() {
		Reservation item = createTestReservation();
		reservationManager.createReservation(item);

		assertNotNull(item.getId());
		Restaurant restaurant = restaurantManager.getRestaurant(item
				.getRestaurant().getId());
		assertTrue(restaurant.getReservations().contains(item));

		User user = userManager.getUser(item.getUser().getId());
		assertTrue(user.getReservations().contains(item));
	}

	@Test
	public void removeReservationTest() {
		Reservation item = createTestReservation();
		reservationManager.createReservation(item);
		reservationManager.removeReservation(item);
		assertNull(reservationManager.getReservation(item.getId()));

		Restaurant restaurant = restaurantManager.getRestaurant(item
				.getRestaurant().getId());
		assertFalse(restaurant.getReservations().contains(item));

		User user = userManager.getUser(item.getUser().getId());
		assertFalse(user.getReservations().contains(item));
	}

	@Test
	public void updateReservationTest() {
		Reservation item = createTestReservation();
		reservationManager.createReservation(item);

		item.setTableNumber(3);
		item.setSeats(5);
		item.setTime(new Date());

		reservationManager.updateReservation(item);

		Reservation dbItem = reservationManager.getReservation(item.getId());
		assertTrue(dbItem.getTime().equals(item.getTime()));
		assertTrue(dbItem.getSeats() == item.getSeats());
		assertTrue(dbItem.getTableNumber() == item.getTableNumber());
	}

	@Test
	public void removeAllReservationsTest() {
		Reservation res1 = createTestReservation();
		Reservation res2 = createTestReservation();

		reservationManager.createReservation(res1);
		reservationManager.createReservation(res2);

		reservationManager.removeAllReservations(res1.getUser());

		assertTrue(reservationManager.getUserReservations(res1.getUser())
				.isEmpty());
	}

	@Test
	public void getReservationTest() {
		Reservation item = createTestReservation();
		reservationManager.createReservation(item);

		Reservation dbItem = reservationManager.getReservation(item.getId());

		assertTrue(item.equals(dbItem));
	}

	@Test
	public void getUserReservationsTest() {
		Reservation res1 = createTestReservation();
		Reservation res2 = createTestReservation();

		reservationManager.createReservation(res1);
		reservationManager.createReservation(res2);

		Collection<Reservation> reservations = reservationManager
				.getUserReservations(res1.getUser());

		assertTrue(reservations.size() == 2 & reservations.contains(res1)
				& reservations.contains(res2));
	}

	@Test
	public void getRestaurantReservationsTest() {
		Reservation res1 = createTestReservation();
		Reservation res2 = createTestReservation();

		reservationManager.createReservation(res1);
		reservationManager.createReservation(res2);

		Collection<Reservation> reservations = reservationManager
				.getRestaurantReservations(res1.getRestaurant());

		assertTrue(reservations.size() == 2 & reservations.contains(res1)
				& reservations.contains(res2));
	}

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap
				// Create test war archive
				.create(WebArchive.class, "restman-test.war")
				// Add necessary sources
				.addPackage("org.jboss.community.pv243.model")
				.addPackage("org.jboss.community.pv243.service")
				.addClass(Resources.class)
				// Deploy persistence descriptor
				.addAsResource("META-INF/test-persistence.xml",
						"META-INF/persistence.xml")
				// Deploy beans descriptor
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				// Deploy our test datasource
				.addAsWebInfResource("test-ds.xml", "test-ds.xml");
	}

	private Reservation createTestReservation() {
		Restaurant restaurant = createPersistTestRestaurant();
		User user = createPersistTestUser();

		Reservation reservation = new Reservation();
		reservation.setRestaurant(restaurant);
		reservation.setUser(user);
		reservation.setTime(new Date());
		reservation.setTableNumber(1);
		reservation.setSeats(4);
		reservation.setReservedMenu(restaurant.getMenu());
		return reservation;
	}

	private User createPersistTestUser() {
		User newUser = new User();
		newUser.setEmail("testuser" + new Date().getTime() + "@redhat.com");
		newUser.setPassword("pwd1");
		newUser.setFirstName("test");
		newUser.setSecondName("user");
		newUser.setReservations(new ArrayList<Reservation>());

		userManager.registerUser(newUser);

		return newUser;
	}

	private Restaurant createPersistTestRestaurant() {
		Restaurant newRestaurant = new Restaurant();
		newRestaurant.setEmail("restaurant" + new Date().getTime()
				+ "@redhat.com");
		newRestaurant.setAddress("Purkynova 12");
		newRestaurant.setPassword("pwd2");
		newRestaurant.setInformation("Basic info");
		newRestaurant.setName("RestaurantName" + new Date());
		restaurantManager.createRestaurant(newRestaurant);
		
		MenuItem item = new MenuItem();
		item.setName("snicl");
		item.setPrize(200);
		item.setWeight(150);
		item.setRestaurant(newRestaurant);
		menuManager.createMenuItem(item);

		ArrayList<MenuItem> menu = new ArrayList<MenuItem>();
		menu.add(item);

		newRestaurant.setMenu(menu);
		restaurantManager.updateRestaurant(newRestaurant);

		return newRestaurant;
	}
}

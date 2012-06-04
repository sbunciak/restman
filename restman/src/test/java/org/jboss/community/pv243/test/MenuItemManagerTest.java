package org.jboss.community.pv243.test;

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
public class MenuItemManagerTest {

	@Inject
	MenuItemManager manager;

	@Inject
	RestaurantManager restaurantManager;

	@Inject
	ReservationManager reservationManager;
	@Inject
	UserManager userManager;

	@Test
	public void createMenuItemTest() {
		MenuItem item = createTestMenuItem();
		manager.createMenuItem(item);

		assertNotNull(item.getId());
	}

	@Test
	public void updateMenuItemTest() {
		MenuItem item = createTestMenuItem();
		manager.createMenuItem(item);

		item.setName("FoodName2");
		item.setPrize(1002);
		item.setWeight(152);

		manager.updateMenuItem(item);

		MenuItem dbItem = manager.getMenuItem(item.getId());
		assertTrue(dbItem.getName().equals(item.getName()));
		assertTrue(dbItem.getPrize() == item.getPrize());
		assertTrue(dbItem.getWeight() == item.getWeight());
	}

	@Test
	public void removeMenuItemTest() {
		MenuItem item = createTestMenuItem();
		manager.createMenuItem(item);
		manager.removeMenuItem(item);
		assertNull(manager.getMenuItem(item.getId()));
	}

	@Test
	public void getMenuItemTest() {
		MenuItem item = createTestMenuItem();
		manager.createMenuItem(item);

		MenuItem dbItem = manager.getMenuItem(item.getId());

		assertTrue(item.equals(dbItem));
	}

	@Test
	public void getRestaurantMenuTest() {
		Restaurant res = createPersistTestRestaurant();

		Collection<MenuItem> menu = res.getMenu();

		manager.getRestaurantMenu(res).equals(menu);
	}

	@Test
	public void getReservationMenuTest() {
		Reservation reservation = createTestReservation();
		reservationManager.createReservation(reservation);

		Collection<MenuItem> menu = reservation.getReservedMenu();

		assertTrue(manager.getReservationMenu(reservation).equals(menu));
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

	private MenuItem createTestMenuItem() {
		MenuItem item = new MenuItem();
		item.setName("snicl");
		item.setPrize(200);
		item.setWeight(150);
		return item;
	}

	private Restaurant createPersistTestRestaurant() {
		Restaurant newRestaurant = new Restaurant();
		newRestaurant.setEmail("restaurant" + new Date().getTime()
				+ "@redhat.com");
		newRestaurant.setAddress("Purkynova 12");
		newRestaurant.setPassword("pwd2");
		newRestaurant.setInformation("Basic info");
		newRestaurant.setName("RestaurantName" + new Date());
		newRestaurant.setMenu(new ArrayList<MenuItem>());

		restaurantManager.createRestaurant(newRestaurant);
		MenuItem item = createTestMenuItem();
		item.setRestaurant(newRestaurant);
		manager.createMenuItem(item);

		ArrayList<MenuItem> menu = new ArrayList<MenuItem>();
		menu.add(item);

		newRestaurant.setMenu(menu);
		restaurantManager.updateRestaurant(newRestaurant);

		return newRestaurant;
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
}

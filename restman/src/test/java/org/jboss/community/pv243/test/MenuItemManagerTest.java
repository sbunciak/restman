package org.jboss.community.pv243.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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
	MenuItemManager menuItemManager;

	@Inject
	RestaurantManager restaurantManager;

	@Inject
	ReservationManager reservationManager;
	
	@Inject
	UserManager userManager;

	@Test
	public void createMenuItemTest() {
		MenuItem item = createTestMenuItem();
		menuItemManager.createMenuItem(item);

		assertNotNull(item.getId());
	}

	@Test
	public void updateMenuItemTest() {
		MenuItem item = createTestMenuItem();
		menuItemManager.createMenuItem(item);

		item.setName("ChangedName");
		item.setPrize(1002);
		item.setWeight(152);

		menuItemManager.updateMenuItem(item);

		MenuItem dbItem = menuItemManager.getMenuItem(item.getId());
		assertTrue(dbItem.getName().equals(item.getName()));
		assertTrue(dbItem.getPrize() == item.getPrize());
		assertTrue(dbItem.getWeight() == item.getWeight());
	}

	@Test
	public void removeMenuItemTest() {
		MenuItem item = createTestMenuItem();
		menuItemManager.createMenuItem(item);
		menuItemManager.removeMenuItem(item);
		assertNull(menuItemManager.getMenuItem(item.getId()));
	}

	@Test
	public void getMenuItemTest() {
		MenuItem item = createTestMenuItem();
		menuItemManager.createMenuItem(item);

		MenuItem dbItem = menuItemManager.getMenuItem(item.getId());

		assertTrue(item.equals(dbItem));
	}

	@Test
	public void getRestaurantMenuTest() {
		Restaurant res = createPersistTestRestaurant();

		Collection<MenuItem> menu = res.getMenu();

		menuItemManager.getRestaurantMenu(res).equals(menu);
	}

	@Test
	public void getReservationMenuTest() {
		Restaurant restaurant = createPersistTestRestaurant();
		
		Reservation reservation = createTestReservation();
		reservation.setUser(createPersistTestUser());
		reservation.setRestaurant(restaurant);
		reservationManager.createReservation(reservation);
		reservation.setReservedMenu(restaurant.getMenu());
		reservationManager.updateReservation(reservation);

		Collection<MenuItem> menu = reservation.getReservedMenu();

		assertTrue(Arrays.equals(menuItemManager.getReservationMenu(
				reservation).toArray(), menu.toArray()));
		
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
		newRestaurant.setName("RestaurantName");
		newRestaurant.setMenu(new ArrayList<MenuItem>());

		MenuItem item = createTestMenuItem();
		item.setRestaurant(newRestaurant);
		newRestaurant.getMenu().add(item);
		
		restaurantManager.createRestaurant(newRestaurant);

		return newRestaurant;
	}

	private Reservation createTestReservation() {
		Reservation reservation = new Reservation();
		reservation.setTime(new Date(System.currentTimeMillis() + 1000));
		reservation.setTableNumber(1);
		reservation.setSeats(4);
		return reservation;
	}

	private User createPersistTestUser() {
		User newUser = new User();
		newUser.setEmail("testuser" + new Date().getTime() % 10 + "@redhat.com");
		newUser.setPassword("pwd1");
		newUser.setFirstName("test");
		newUser.setSecondName("user");
		newUser.setPhoneNumber(new BigDecimal("0907123123"));
		newUser.setReservations(new ArrayList<Reservation>());

		userManager.registerUser(newUser);

		return newUser;
	}
}

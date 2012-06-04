package org.jboss.community.pv243.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.community.pv243.model.MenuItem;
import org.jboss.community.pv243.model.Restaurant;
import org.jboss.community.pv243.service.RestaurantManager;
import org.jboss.community.pv243.util.Resources;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class RestaurantManagerTest {

	@Inject
	RestaurantManager manager;

	@Test
	public void authRestaurantTest() {
		Restaurant newRestaurant = createTestRestaurant();

		manager.createRestaurant(newRestaurant);
		Restaurant dbRes = manager.authRestaurant(newRestaurant.getEmail(),
				newRestaurant.getPassword());

		assertNotNull(newRestaurant.equals(dbRes));
	}

	@Test
	public void createRestaurantTest() {
		Restaurant newRestaurant = createTestRestaurant();

		manager.createRestaurant(newRestaurant);

		assertNotNull(newRestaurant.getId());
	}

	@Test
	public void deleteRestaurantTest() {
		Restaurant newRestaurant = createTestRestaurant();

		manager.createRestaurant(newRestaurant);
		manager.deleteRestaurant(newRestaurant);

		assertNull(manager.getRestaurant(newRestaurant.getId()));
	}

	@Test
	public void updateRestaurantTest() {
		Restaurant oldRestaurant = createTestRestaurant();

		manager.createRestaurant(oldRestaurant);

		oldRestaurant.setInformation("information2");
		oldRestaurant.setName("name2");

		manager.updateRestaurant(oldRestaurant);

		Restaurant newRestaurant = manager.getRestaurant(oldRestaurant.getId());
		assertTrue(newRestaurant.getInformation().equals("information2"));
		assertTrue(newRestaurant.getName().equals("name2"));
	}

	@Test
	public void getRestaurantTest() {
		Restaurant newRestaurant = createTestRestaurant();

		manager.createRestaurant(newRestaurant);

		Restaurant dbRestaurant = manager.getRestaurant(newRestaurant.getId());

		assertTrue(newRestaurant.equals(dbRestaurant));
		assertTrue(newRestaurant.getMenu().equals(createTestMenu()));
	}

	@Test
	public void getAllRestaurantsTest() {
		for (Restaurant r : manager.getAllRestaurants()) {
			manager.deleteRestaurant(r);
		}

		assertTrue(manager.getAllRestaurants().isEmpty());

		Restaurant newRestaurant = createTestRestaurant();

		manager.createRestaurant(newRestaurant);

		assertTrue(manager.getAllRestaurants().size() == 1);
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

	private Restaurant createTestRestaurant() {
		Restaurant newRestaurant = new Restaurant();
		newRestaurant.setEmail("restaurant" + new Date().getTime()
				+ "@redhat.com");
		newRestaurant.setAddress("Purkynova 12");
		newRestaurant.setPassword("pwd2");
		newRestaurant.setInformation("Basic info");
		newRestaurant.setName("RestaurantName" + new Date());
		newRestaurant.setMenu(createTestMenu());
		return newRestaurant;
	}

	private List<MenuItem> createTestMenu() {
		return new ArrayList<MenuItem>();
	}
}

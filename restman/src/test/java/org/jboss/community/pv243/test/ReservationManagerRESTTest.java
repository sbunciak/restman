package org.jboss.community.pv243.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.community.pv243.model.MenuItem;
import org.jboss.community.pv243.model.Reservation;
import org.jboss.community.pv243.model.Restaurant;
import org.jboss.community.pv243.model.User;
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

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

@RunWith(Arquillian.class)
public class ReservationManagerRESTTest {
	@Inject
	ReservationManager reservationManager;
	
	@Inject
	UserManager userManager;
	
	@Inject
	RestaurantManager restaurantManager;
	
	private static final String BASE_URL = "http://localhost:8080/restman-test";
	
	@Test
	public void getReservation() throws JAXBException {
		Reservation newReservation = createTestReservation();
		reservationManager.createReservation(newReservation, 
				createPersistTestUser(), createPersistTestRestaurant());

		Reservation dbReservation = reservationManager.getReservation(newReservation.getId());

		assertTrue(newReservation.equals(dbReservation));
		
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(BASE_URL);

		// Get XML for application
		String response = service.path("rest").path("reservations")
				.path(String.valueOf(newReservation.getId()))
				.get(String.class);

		JAXBContext jc = JAXBContext.newInstance(Reservation.class);
		Unmarshaller u = jc.createUnmarshaller();
		
		StringBuffer xmlStr = new StringBuffer(response);
		Reservation restReservation = (Reservation) u.unmarshal(new StreamSource(
				new StringReader(xmlStr.toString())));

		assertTrue(restReservation.equals(dbReservation));
	}
	
	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap
				// Create test war archive
				.create(WebArchive.class, "restman-test.war")
				// Add necessary sources
				.addPackage("org.jboss.community.pv243.model")
				.addPackage("org.jboss.community.pv243.service")
				.addPackage("org.jboss.community.pv243.rest")
				.addAsLibraries(new File("target/test-libs/jersey-core.jar"))
				.addAsLibraries(new File("target/test-libs/jersey-client.jar"))
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
		Reservation reservation = new Reservation();
		reservation.setTime(new Date(System.currentTimeMillis() + 1000));
		reservation.setTableNumber(1);
		reservation.setSeats(4);
		return reservation;
	}
	
	private User createPersistTestUser() {
		User newUser = new User();
		newUser.setEmail("testuser" + new Date().getTime() + "@redhat.com");
		newUser.setPassword("pwd1");
		newUser.setFirstName("test");
		newUser.setSecondName("user");
		newUser.setPhoneNumber(new BigDecimal("0907123123"));
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
		newRestaurant.setName("RestaurantName");
		
		MenuItem item = new MenuItem();
		item.setName("snicl");
		item.setPrize(200);
		item.setWeight(150);
		item.setRestaurant(newRestaurant);

		ArrayList<MenuItem> menu = new ArrayList<MenuItem>();
		menu.add(item);

		newRestaurant.setMenu(menu);
		
		ArrayList<Reservation> reservations = new ArrayList<Reservation>();
		newRestaurant.setReservations(reservations);
		
		restaurantManager.createRestaurant(newRestaurant);

		return newRestaurant;
	}

}

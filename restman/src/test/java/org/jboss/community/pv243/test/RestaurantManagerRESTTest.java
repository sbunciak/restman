package org.jboss.community.pv243.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

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

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

@RunWith(Arquillian.class)
public class RestaurantManagerRESTTest {

	@Inject
	RestaurantManager manager;

	private static final String BASE_URL = "http://localhost:8080/restman-test";
	
	@Test
	public void getRestaurant() throws JAXBException {

		Restaurant newRestaurant = createTestRestaurant();

		manager.createRestaurant(newRestaurant);

		Restaurant dbRestaurant = manager.getRestaurant(newRestaurant.getId());

		// http://www.vogella.com/articles/REST/article.html
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(BASE_URL);

		// Get XML for application
		String response = service.path("rest").path("restaurants")
				.path(String.valueOf(newRestaurant.getId()))
				.get(String.class);

		JAXBContext jc = JAXBContext.newInstance(Restaurant.class);
		Unmarshaller u = jc.createUnmarshaller();
		
		StringBuffer xmlStr = new StringBuffer(response);
		Restaurant restRestaurant = (Restaurant) u.unmarshal(new StreamSource(
				new StringReader(xmlStr.toString())));

		assertTrue(restRestaurant.equals(dbRestaurant));
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

	private Restaurant createTestRestaurant() {
		Restaurant newRestaurant = new Restaurant();
		newRestaurant.setEmail("restaurant" + new Date().getTime()
				+ "@redhat.com");
		newRestaurant.setAddress("Purkynova 12");
		newRestaurant.setPassword("pwd2");
		newRestaurant.setInformation("Basic info");
		newRestaurant.setName("RestaurantName");
		newRestaurant.setMenu(createTestMenu());
		return newRestaurant;
	}

	private List<MenuItem> createTestMenu() {
		return new ArrayList<MenuItem>();
	}

}

package org.jboss.community.pv243.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
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
public class RestaurantManagerRESTTest {

	@Inject
	RestaurantManager manager;

	/**
	 * http://docs.oracle.com/javase/6/docs/api/javax/xml/bind/JAXBContext.html
	 * http://docs.oracle.com/javaee/6/tutorial/doc/gkoib.html
	 * http://stackoverflow.com/questions/2461232/jaxb-entity-print-out-as-xml
	 * http://www.slideshare.net/shaunmsmith/restful-services-with-jaxb-and-jpa
	 * https://community.jboss.org/wiki/JBossAS7ConfiguringSSLOnJBossWeb
	 * https://
	 * docs.jboss.org/author/display/AS7/Security+subsystem+configuration
	 * http://middlewaremagic.com/jboss/?p=992
	 * 
	 * 
	 * finish rest (+ secured) implement ui of finish sign off docs deploy to
	 * openshift
	 * 
	 * @return
	 * @throws JAXBException
	 * @throws IOException
	 * @throws HttpException
	 */

	@Test
	public void getRestaurant() throws JAXBException, IOException {

		Restaurant newRestaurant = createTestRestaurant();

		manager.createRestaurant(newRestaurant);

		Restaurant dbRestaurant = manager.getRestaurant(newRestaurant.getId());
		/*
		 * String response =
		 * method.getResponseBodyAsString();
		 * 
		 * JAXBContext jc = JAXBContext.newInstance(Restaurant.class);
		 * Unmarshaller u = jc.createUnmarshaller();
		 * 
		 * StringBuffer xmlStr = new StringBuffer(response); Restaurant
		 * restRestaurant = (Restaurant) u.unmarshal(new StreamSource( new
		 * StringReader(xmlStr.toString())));
		 * 
		 * assertTrue(restRestaurant.equals(dbRestaurant));
		 * assertTrue(Arrays.equals(restRestaurant.getMenu().toArray(),
		 * createTestMenu().toArray()));
		 */

		//http://www.vogella.com/articles/REST/article.html
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource("http://localhost:8080/restman");

		// Get XML for application
		System.out.println(service.path("rest").path("restaurants")
				//.accept(MediaType.APPLICATION_JSON)
				.get(String.class));

	}

	@Test
	public void getAllRestaurants() {
	}

	@Test
	public void getMenuOfRestaurant() {
	}

	@Test
	public void getReservationsOfRestaurant() {
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
				.addAsLibraries(
						new File("target/test-libs/jersey-core.jar"))
				.addAsLibraries(
						new File("target/test-libs/jersey-client.jar"))
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

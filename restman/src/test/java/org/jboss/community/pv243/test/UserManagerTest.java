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
import org.jboss.community.pv243.model.Reservation;
import org.jboss.community.pv243.model.User;
import org.jboss.community.pv243.service.UserManager;
import org.jboss.community.pv243.util.Resources;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class UserManagerTest {

	@Inject
	UserManager userManager;

	@Test
	public void authUserTest() {
		User newUser = createTestUser();
		userManager.registerUser(newUser);

		User dbUser = userManager.authUser(newUser.getEmail(),
				newUser.getPassword());

		assertTrue(newUser.equals(dbUser));
	}

	@Test
	public void registerUserTest() {
		User newUser = createTestUser();
		userManager.registerUser(newUser);

		assertNotNull(newUser.getId());
	}

	@Test
	public void deleteUserTest() {
		User newUser = createTestUser();

		userManager.registerUser(newUser);
		userManager.deleteUser(newUser);

		assertNull(userManager.getUser(newUser.getId()));
	}

	@Test
	public void updateUser() {
		User newUser = createTestUser();

		userManager.registerUser(newUser);

		newUser.setFirstName("Changed");
		newUser.setSecondName("User2");
		userManager.updateUser(newUser);

		User dbUser = userManager.getUser(newUser.getId());

		assertTrue(dbUser.getFirstName().equals("Changed"));
		assertTrue(dbUser.getSecondName().equals("User2"));
	}

	@Test
	public void getUser() {
		
		User newUser = createTestUser();

		userManager.registerUser(newUser);

		User dbUser = userManager.getUser(newUser.getId());

		assertTrue(dbUser.equals(newUser));
		// TODO: assertion (class not found on dbUser.getReservations(), lazy fetching)
		assertTrue(dbUser.getReservations().equals(createTestReservations()));
	}

	@Test
	public void getAllUsers() {
		for (User u : userManager.getAllUsers()) {
			userManager.deleteUser(u);
		}

		assertTrue(userManager.getAllUsers().isEmpty());

		User newUser = createTestUser();

		userManager.registerUser(newUser);

		assertTrue(userManager.getAllUsers().size() == 1);
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

	private User createTestUser() {
		User newUser = new User();
		newUser.setEmail("testuser" + new Date().getTime() + "@redhat.com");
		newUser.setPassword("pwd1");
		newUser.setFirstName("test");
		newUser.setSecondName("user");
		newUser.setReservations(createTestReservations());
		return newUser;
	}

	private List<Reservation> createTestReservations() {
		return new ArrayList<Reservation>();
	}
}

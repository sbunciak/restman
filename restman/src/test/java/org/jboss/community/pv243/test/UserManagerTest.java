package org.jboss.community.pv243.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
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
	public void testUserLifeCycle() {
		// Create user
		User newUser = new User();
		newUser.setName("test");
		newUser.setSecondName("user");
		userManager.registerUser(newUser);
		
		// TODO: add reservations, integration tests???
		assertNotNull(newUser.getId());
		
		// test if user is fetched from db
		assertTrue(userManager.getAllUsers().size() == 1);
		
		// change user
		newUser.setSecondName("changedUser");
		
		// update user in db
		userManager.updateUser(newUser.getId(), newUser);
		
		// test if the change has been propagated
		assertTrue(userManager.getUser(newUser.getId()).equals(newUser));
		
		// delete user
		userManager.deleteUser(newUser);
		
		// check number of users in db
		assertTrue(userManager.getAllUsers().isEmpty());
	}

	@Deployment
	public static Archive<?> createTestArchive() {
		System.out.println("creating test archive");
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

}

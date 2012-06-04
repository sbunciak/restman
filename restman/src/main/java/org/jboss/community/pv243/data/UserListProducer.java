package org.jboss.community.pv243.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.community.pv243.model.User;
import org.jboss.community.pv243.service.UserManager;


@RequestScoped
public class UserListProducer {

	@Inject
	UserManager userManager;
	
	private List<User> users;
	
	@Produces
	@Named
	public List<User> getUsers(){
		return users;
	}
	
	@PostConstruct
	public void initiateUsers(){
		users = userManager.getAllUsers();
	}
	
	public void onUserListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final User user){
		System.out.println("Test");
		initiateUsers();
	}
	
}

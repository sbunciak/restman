package org.jboss.community.pv243.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.community.pv243.model.User;
import org.jboss.community.pv243.service.UserManager;

@Model
public class UserController {

	@Inject
	FacesContext facesContext;
	
	@Inject
	UserManager userManager;
	
	private User newUser;
	
	@Produces
	@Named
	public User getNewUser(){
		return newUser;
	}
	
	public void registerUser(){
		System.out.println("Registrace uzivatele");
		userManager.registerUser(newUser);
		initiateUser();
	}
	
	@PostConstruct
	public void initiateUser(){
		newUser = new User();
	}
}

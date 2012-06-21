package org.jboss.community.pv243.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.community.pv243.model.User;
import org.jboss.community.pv243.service.UserManager;
import java.io.Serializable;

@Named
@SessionScoped
public class UserController implements Serializable {

	private static final long serialVersionUID = 572238792634619785L;

	@Inject
	FacesContext facesContext;
	
	@Inject
	UserManager userManager;
	
	private User newUser;
	
	private boolean edit = false;
	
	@Produces
	@Named
	public User getNewUser(){
		return newUser;
	}
	
	public void registerUser(){
		userManager.registerUser(newUser);
		initiateUser();
	}
	
	public void deleteUser(User user){
		userManager.deleteUser(user);
		initiateUser();
	}
	
	public void editUser(User user){
		newUser = userManager.getUser(user.getId());
		edit = true;
	}
	
	public void save() {
		userManager.updateUser(newUser);
		edit = false;
		initiateUser();
	}
	
	public void clear() {
		edit = false;
		initiateUser();
	}
	
	public boolean isEdit() {
		return edit;
	}

	@PostConstruct
	public void initiateUser(){
		newUser = new User();
	}
	
	public void updateUser(User u) {
		userManager.updateUser(u);
	}
}

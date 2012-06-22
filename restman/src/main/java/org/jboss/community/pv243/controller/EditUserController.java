package org.jboss.community.pv243.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.community.pv243.model.User;
import org.jboss.community.pv243.service.UserManager;

@Named
@SessionScoped
public class EditUserController implements Serializable {

	private static final long serialVersionUID = 6987051567930204090L;

		@Inject
		UserManager userManager;
		
		@Inject
		FacesContext facesContext;
		
		private User user;

		private boolean edit = false;
		
		public User initLoggedUser(){
			if (!facesContext.getExternalContext().isUserInRole("USER")){
				throw new IllegalStateException("Uzivatel neni v roli USER");
			}
			if (user== null){
				user = userManager.getUserByEmail(facesContext.getExternalContext().getUserPrincipal().getName());
			}
			return user;
		}
		
		public void updateUser() {
			userManager.updateUser(user);
			edit = true;
		}

		public boolean isEdit() {
			return edit;
		}

		public void setEdit(boolean edit) {
			this.edit = edit;
		}
		
		@Produces
		@Named
		public User getLoggedUser(){
			return user;
		}
		
		@PostConstruct
		public void initiateMenuItems(){
			user = initLoggedUser();
		}
		
}

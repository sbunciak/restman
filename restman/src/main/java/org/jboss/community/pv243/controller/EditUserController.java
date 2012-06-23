package org.jboss.community.pv243.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.community.pv243.model.User;
import org.jboss.community.pv243.service.UserManager;

@Named
@ConversationScoped
public class EditUserController implements Serializable {

	private static final long serialVersionUID = 6987051567930204090L;

		@Inject
		UserManager userManager;
		
		@Inject
		FacesContext facesContext;
		
		private User user;

		private boolean edit = false;
		
		public User initLoggedUser(){
			if (facesContext.getExternalContext().getUserPrincipal()== null){
				return new User();
			}
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
		
		public void createUser(){
			userManager.registerUser(user);
			initiateUser();
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
					"Registration successful", "New user was successfuly created"));
			try {
				facesContext.getExternalContext().redirect("/restman");
			} catch (IOException e) {
				e.printStackTrace();
			}
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
		public void initiateUser(){
			user = initLoggedUser();
		}
		
}

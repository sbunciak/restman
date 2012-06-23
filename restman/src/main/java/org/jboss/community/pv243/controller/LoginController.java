package org.jboss.community.pv243.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jboss.community.pv243.model.Restaurant;
import org.jboss.community.pv243.model.User;
import org.jboss.community.pv243.service.RestaurantManager;
import org.jboss.community.pv243.service.UserManager;


@ManagedBean(name="loginController")
@SessionScoped
public class LoginController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6715242554246206059L;

	@Inject
	FacesContext facesContext;
	
	@Inject
	RestaurantManager restaurantManager;

	@Inject
	UserManager manager;

	public void logout() {
		try {
			HttpSession session = (HttpSession) facesContext
					.getExternalContext().getSession(true);
			session.invalidate();
			facesContext.getExternalContext().redirect("/restman");
		} catch (Exception e) {
			throw new SecurityException(e);
		} 
	}

	public void login() {
		HttpServletRequest request = (HttpServletRequest) facesContext
				.getExternalContext().getRequest();
		HttpServletResponse response = (HttpServletResponse) facesContext
				.getExternalContext().getResponse();
		try {
			if (!request.authenticate(response)) {
				facesContext.responseComplete();
				System.out.println(facesContext.getExternalContext()
						.getRemoteUser());
			}
		} catch (Exception e) { // may throw ServletException or IOException
			e.printStackTrace();
		}
		return;
	}

	public boolean isLogged() {
		return facesContext.getExternalContext().getUserPrincipal() != null;
	}

	public boolean isManager() {
		if (facesContext.getExternalContext().isUserInRole("MANAGER")) {
			return true;
		}
		return false;
	}

	public boolean isUser() {
		if (facesContext.getExternalContext().isUserInRole("USER")) {
			return true;
		}
		return false;
	}

	public boolean isAdmin() {
		if (facesContext.getExternalContext().isUserInRole("ADMIN")) {
			return true;
		}
		return false;
	}
	
	public void redirect(){
		try {
			facesContext.getExternalContext().redirect("/restman");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Produces
	@Named
	public User getLoggedUser() {
		return manager.authUser(facesContext.getExternalContext()
				.getUserPrincipal().getName());
	}
}

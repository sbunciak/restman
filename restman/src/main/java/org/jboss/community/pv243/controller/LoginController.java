package org.jboss.community.pv243.controller;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.security.auth.login.LoginContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Model
public class LoginController {
	
	@Inject
	FacesContext facesContext;
	
	public void logout(){
		//facesContext.getExternalContext().getSessionMap().put("sourcePageForLogin", facesContext.getExternalContext().getRequestPathInfo())
		facesContext.getExternalContext().invalidateSession();
		HttpServletRequest request = (HttpServletRequest)facesContext.getExternalContext().getRequest();
		try {
			request.logout();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

	public void login(){
		HttpServletRequest request = (HttpServletRequest)facesContext.getExternalContext().getRequest();
		HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
		try {
			if (!request.authenticate(response)) {
			    FacesContext.getCurrentInstance().responseComplete();
			}
		} catch (Exception e) { // may throw ServletException or IOException
		    e.printStackTrace();
		}
		return;
	}
	
	public boolean isLogged(){
		return facesContext.getExternalContext().getUserPrincipal() != null;
	}
	
	public boolean isManager(){
		if (facesContext.getExternalContext().isUserInRole("MANAGER")){
			return true;
		}
		return false;
	}
	
	public boolean isUser(){
		if (facesContext.getExternalContext().isUserInRole("USER")){
			return true;
		}
		return false;
	}
	
	public boolean isAdmin(){
		if (facesContext.getExternalContext().isUserInRole("ADMIN")){
			return true;
		}
		return false;
	}
	
}

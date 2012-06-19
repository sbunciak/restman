package org.jboss.community.pv243.controller;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;


@Model
public class LoginController {
	
	@Inject
	FacesContext facesContext;
	
	public void logout(){
		System.out.println("Odhlasuju");
		facesContext.getExternalContext().invalidateSession();
	}

}

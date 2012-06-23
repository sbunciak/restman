/*package org.jboss.community.pv243.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.jboss.community.pv243.model.User;
import org.jboss.community.pv243.service.UserManager;


@FacesConverter(value="userConverter", forClass = User.class)
public class UserConveter implements Converter{

	@Inject
	UserManager userManager;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		int id = Integer.valueOf(value);
		return userManager.getUser(id);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		return String.valueOf(((User)value).getId());
	}

}*/

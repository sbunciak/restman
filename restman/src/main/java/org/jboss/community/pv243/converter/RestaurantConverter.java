package org.jboss.community.pv243.converter;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.community.pv243.model.Restaurant;
import org.jboss.community.pv243.service.RestaurantManager;

@Named
@RequestScoped
public class RestaurantConverter implements Converter {

	@Inject
	RestaurantManager rm;
	
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String newValue) {
        Restaurant r = rm.getRestaurantByName(newValue);
        System.out.println(r == null);
        return r;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((Restaurant)value).getName();
    }

}

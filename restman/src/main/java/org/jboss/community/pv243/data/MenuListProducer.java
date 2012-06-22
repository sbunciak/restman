package org.jboss.community.pv243.data;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.community.pv243.model.MenuItem;
import org.jboss.community.pv243.model.Restaurant;
import org.jboss.community.pv243.service.MenuItemManager;
import org.jboss.community.pv243.service.RestaurantManager;

@ManagedBean
@RequestScoped
public class MenuListProducer {

		@Inject
		MenuItemManager menuItemManager;
		
		@Inject
		FacesContext facesContext;
		
		@Inject
		RestaurantManager restaurantManager;
		
		private Collection<MenuItem> menuItems;
		
		private Restaurant restaurant;

		
		public Restaurant getLoggedRestaurant(){
			if (!facesContext.getExternalContext().isUserInRole("MANAGER")){
				throw new IllegalStateException("Uzivatel neni v roli MANAGER");
			}
			if (restaurant== null){
			restaurant = restaurantManager.getRestaurantByEmail(facesContext.getExternalContext().getUserPrincipal().getName());
			}
			return restaurant;
		}
		

		@Produces
		@Named
		public Collection<MenuItem> getMenuItems(){
			return menuItems;
		}
		
		@PostConstruct
		public void initiateMenuItems(){
			menuItems = menuItemManager.getRestaurantMenu(getLoggedRestaurant());
			/*for (MenuItem mi : menuItems) {
				System.out.println(mi.get);
			}*/
		}
		
		/*public void onMenuItemListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final MenuItem menuItem){
			initiateMenuItems();
		}*/

}

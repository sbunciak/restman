package org.jboss.community.pv243.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity implementation class for Entity: Restaurant
 *
 */
@Entity
@XmlRootElement
public class Restaurant extends AbstractEntity implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private Collection<MenuItem> menu;
	private String information;

	public Restaurant() {
		super();
	}

	public Collection<MenuItem> getMenu() {
		return menu;
	}

	public void setMenu(Collection<MenuItem> menu) {
		this.menu = menu;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}
   
}

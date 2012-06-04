package org.jboss.community.pv243.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity implementation class for Entity: Restaurant
 *
 */
@Entity
@XmlRootElement
@NamedQuery(name="Restaurant.findAll", query="SELECT r FROM Restaurant r")
public class Restaurant implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_restaurant")
	private int idRestaurant;
	
	private String name;
	
	private String information;
	
	private String address;
	
	@OneToMany(fetch=FetchType.LAZY, 
			   cascade=CascadeType.ALL, mappedBy = "restaurant")
	private Collection<Reservation> reservations;
	
	@OneToMany(fetch=FetchType.LAZY, 
			   cascade=CascadeType.ALL, mappedBy = "restaurant")
	private Collection<MenuItem> menu;
	
	public int getIdRestaurant() {
		return idRestaurant;
	}

	public void setIdRestaurant(int idRestaurant) {
		this.idRestaurant = idRestaurant;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Collection<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(Collection<Reservation> reservations) {
		this.reservations = reservations;
	}

	// TODO: lazy fetch throws exception
	public Collection<MenuItem> getMenu() {
		return menu;
	}

	public void setMenu(Collection<MenuItem> menu) {
		this.menu = menu;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Restaurant other = (Restaurant) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
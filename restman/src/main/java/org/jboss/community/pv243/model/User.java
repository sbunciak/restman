package org.jboss.community.pv243.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity implementation class for Entity: User
 *
 */
@Entity
@XmlRootElement
public class User extends AbstractUser implements Serializable {

	private static final long serialVersionUID = 1L;
	@OneToMany
	private Collection<Reservation> reservations; 

	public User() {
		super();
	}
	
	public Collection<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
   
}

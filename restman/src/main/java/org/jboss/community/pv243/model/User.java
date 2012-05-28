package org.jboss.community.pv243.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity implementation class for Entity: User
 * 
 */
@Entity
@XmlRootElement
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User extends AbstractUser implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@OneToMany(cascade=CascadeType.PERSIST)
	private Collection<Reservation> reservations;

	public User() {
		super();
	}

	public Collection<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(Collection<Reservation> reservations) {
		this.reservations = reservations;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO: Better handling !!!
		return this.getId() == ((User) obj).getId();
	}

}

package org.jboss.community.pv243.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity implementation class for Entity: Reservation
 *
 */
@Entity
@XmlRootElement
public class Reservation implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_reservation")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "id_user")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "id_restaurant")
	private Restaurant restaurant;
	
	@ManyToMany
	@JoinTable(
		name="reserved_menu_items",
		joinColumns={
			@JoinColumn(name="id_reservation", 
					referencedColumnName="id_reservation")
		},
		inverseJoinColumns={
			@JoinColumn(name="id_menu_item", 
					referencedColumnName="id_menu_item")
		}
	)
	private Collection<MenuItem> reservedMenu;
	
	@Future
	private Date time;
	
	// TODO: table management, number of available seats, tables, etc.
	@Column(name="table_number")
	private int tableNumber;
	
	@Min(1)
	private int seats;

	public int getId() {
		return id;
	}

	public void setId(int idReservation) {
		this.id = idReservation;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}
	
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public int getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(int tableNumber) {
		this.tableNumber = tableNumber;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public Collection<MenuItem> getReservedMenu() {
		return reservedMenu;
	}

	public void setReservedMenu(Collection<MenuItem> reservedMenu) {
		this.reservedMenu = reservedMenu;
	}

	
	// TODO: override toString for logging (simpler code)
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + seats;
		result = prime * result
				+ ((restaurant == null) ? 0 : restaurant.hashCode());
		result = prime * result + tableNumber;
		result = prime * result + ((time == null) ? 0 : time.hashCode());
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
		Reservation other = (Reservation) obj;
		if (seats != other.seats)
			return false;
		if (restaurant == null) {
			if (other.restaurant != null)
				return false;
		} else if (!restaurant.equals(other.restaurant))
			return false;
		if (tableNumber != other.tableNumber)
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		return true;
	}

}

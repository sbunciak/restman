package org.jboss.community.pv243.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity implementation class for Entity: User
 * 
 */
@Entity
@XmlRootElement
@NamedQueries({
		@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
		@NamedQuery(name = "User.auth", query = "SELECT u FROM User u WHERE u.email = :email") })
public class User extends AbstractUser implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "first_name")
	@Size(min = 1, max = 25, message="Length of first name must be between 1 and 25 ")
	@Pattern(regexp = "[A-Za-z ]*", message = "First name must contain only letters and spaces")
	private String firstName;

	@Column(name = "second_name")
	@Size(min = 1, max = 25, message="Length of second name must be between 1 and 25 ")
	@Pattern(regexp = "[A-Za-z ]*", message = "First name must contain only letters and spaces")
	private String secondName;

	@Column(name = "phone_number")
	@NotNull(message="Please enter your phone number")
	@Digits(fraction = 0, integer = 12, message="Phone number must contain only digits")
	private BigDecimal phoneNumber;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
	private Collection<Reservation> reservations;



	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public BigDecimal getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(BigDecimal phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Collection<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(Collection<Reservation> reservations) {
		this.reservations = reservations;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result
				+ ((secondName == null) ? 0 : secondName.hashCode());
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
		User other = (User) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (secondName == null) {
			if (other.secondName != null)
				return false;
		} else if (!secondName.equals(other.secondName))
			return false;
		return true;
	}

}

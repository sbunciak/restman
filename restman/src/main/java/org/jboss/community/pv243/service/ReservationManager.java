package org.jboss.community.pv243.service;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.community.pv243.model.MenuItem;
import org.jboss.community.pv243.model.Reservation;
import org.jboss.community.pv243.model.User;

@Stateless
@RequestScoped
public class ReservationManager {

	@Inject
	private EntityManager em;

	@Inject
	private MenuManager mm;

	@Inject
	private Logger log;

	public void createReservation(int id_u, Reservation res) {
		User user = em.find(User.class, id_u);
		user.getReservations().add(res);

		log.info("Reservation: " + res.getId() + " was created for user: name="
				+ user.getName() + " " + user.getSecondName());

	}

	public void removeReservation(int id_u, Reservation res) {
		User user = em.find(User.class, id_u);
		user.getReservations().remove(res);

		log.info("Reservation: " + res.getId()
				+ " was removed from user: name=" + user.getName() + " "
				+ user.getSecondName());

	}

	public void removeAllReservations(int id_u) {
		User user = em.find(User.class, id_u);
		user.getReservations().clear();

		log.info("All reservation were removed from user: name="
				+ user.getName() + " " + user.getSecondName());

	}

	public void updateReservation(int id_u, int id_r, Reservation res) {
		User user = em.find(User.class, id_u);
		Iterator<Reservation> iter = user.getReservations().iterator();
		Reservation reservation = null;
		while (iter.hasNext()) {
			Reservation temp = iter.next();
			if (temp.getId() == id_r) {
				reservation = temp;
				break;
			}
		}
		try {
			em.getTransaction().begin();
			reservation.setTime(res.getTime());
			reservation.setTableNumber(res.getTableNumber());
			reservation.setNumberOfSeats(res.getNumberOfSeats());
			reservation.setMenuItems(res.getMenuItems());
			em.getTransaction().commit();
			log.info("Reservation: " + id_r + " was updated for user: name="
					+ user.getName() + " " + user.getSecondName());
		} finally {
			em.close();
		}
	}

	public void updateReservation(int id_u, int id_r, Date time,
			int tableNumber, int numberOfSeats, Collection<MenuItem> menuItems) {
		User user = em.find(User.class, id_u);
		Iterator<Reservation> iter = user.getReservations().iterator();
		Reservation reservation = null;
		while (iter.hasNext()) {
			Reservation temp = iter.next();
			if (temp.getId() == id_r) {
				reservation = temp;
				break;
			}
		}
		try {
			em.getTransaction().begin();
			reservation.setTime(time);
			reservation.setTableNumber(tableNumber);
			reservation.setNumberOfSeats(numberOfSeats);
			reservation.setMenuItems(menuItems);
			em.getTransaction().commit();
			log.info("Reservation: " + id_r + " was updated for user: name="
					+ user.getName() + " " + user.getSecondName());
		} finally {
			em.close();
		}
	}

	public Reservation getReservation(int id_u, int id_r) {
		User user = em.find(User.class, id_u);
		Iterator<Reservation> iter = user.getReservations().iterator();
		Reservation reservation = null;
		while (iter.hasNext()) {
			Reservation temp = iter.next();
			if (temp.getId() == id_r) {
				reservation = temp;
				break;
			}
		}
		return reservation;
	}

	public Collection<Reservation> getAllReservations(int id_u) {
		User user = em.find(User.class, id_u);
		return user.getReservations();
	}

	public void addMenuItemsIntoReservation(int id_u, int id_r,
			Collection<MenuItem> menuItems) {
		User user = em.find(User.class, id_u);
		Iterator<Reservation> iter = user.getReservations().iterator();
		Reservation reserv = null;
		while (iter.hasNext()) {
			Reservation temp = iter.next();
			if (temp.getId() == id_r) {
				reserv = temp;
				break;
			}
		}
		if (reserv != null) {
			mm.addMenuItemsToMenu(id_r, menuItems);
		}
	}

	public void removeAllMenuItemsFromReservation(int id_u, int id_r) {
		User user = em.find(User.class, id_u);
		Iterator<Reservation> iter = user.getReservations().iterator();
		Reservation reserv = null;
		while (iter.hasNext()) {
			Reservation temp = iter.next();
			if (temp.getId() == id_r) {
				reserv = temp;
				break;
			}
		}
		if (reserv != null) {
			mm.removeWholeMenu(id_r);
		}
	}

	public void removeMenuItemsFromRestaurant(int id_u, int id_r,
			Collection<MenuItem> menuItems) {
		User user = em.find(User.class, id_u);
		Iterator<Reservation> iter = user.getReservations().iterator();
		Reservation reserv = null;
		while (iter.hasNext()) {
			Reservation temp = iter.next();
			if (temp.getId() == id_r) {
				reserv = temp;
				break;
			}
		}
		if (reserv != null) {
			mm.removeMenuItemsFromMenu(id_r, menuItems);
		}
	}

}

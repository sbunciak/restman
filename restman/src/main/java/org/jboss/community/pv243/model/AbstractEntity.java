package org.jboss.community.pv243.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Entity implementation class for Entity: AbstractUser
 *
 */
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

	@Id
	@GeneratedValue
	private int id;
	private String name;
	private static final long serialVersionUID = 1L;

	public AbstractEntity() {
		super();
	}   
	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}   
	
}

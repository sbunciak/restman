package org.jboss.community.pv243.model;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractUser extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	private String secondName;
	
	public String getSecondName() {
		return secondName;
	}
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
	
}

package org.jboss.community.pv243.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity implementation class for Entity: TopManager
 *
 */
@Entity
@XmlRootElement
public class TopManager extends User implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public TopManager() {
		super();
	}
   
}

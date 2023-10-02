/**
 * 
 */
package com.ecole.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author A626257
 *
 */
@Entity
@Table(name = "observationdeli")
public class DeliObservtions implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private AppreciationMS ap;
	
	private DeliberationMS deli;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idobservation")
	public AppreciationMS getAp() {
		return ap;
	}

	public void setAp(AppreciationMS ap) {
		this.ap = ap;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "iddeliberatio")
	public DeliberationMS getDeli() {
		return deli;
	}

	public void setDeli(DeliberationMS deli) {
		this.deli = deli;
	}

}

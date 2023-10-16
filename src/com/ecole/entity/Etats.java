/**
 * 
 */
package com.ecole.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author A626257
 *
 */
@Entity
@Table(name = "etats")
public class Etats implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String libelle;
	
	private Double montant;
	
	private List<TypeDepense> listeDep;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public Double getMontant() {
		return montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}
	
	
	@Transient
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "id")
	public List<TypeDepense> getListeDep() {
		return listeDep;
	}

	public void setListeDep(List<TypeDepense> listeDep) {
		this.listeDep = listeDep;
	}

}

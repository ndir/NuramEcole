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
import javax.persistence.Transient;

/**
 * @author a626257
 *
 */
@Entity
@Table(name="matiere")
public class Matiere implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idmatiere;
	
	private String libelle;
	
	private boolean chosir;
	
	private int coef;
	
	private Niveau niveau;
	
	

	@Id
	@GeneratedValue
	public Long getIdmatiere() {
		return idmatiere;
	}

	public void setIdmatiere(Long idmatiere) {
		this.idmatiere = idmatiere;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}


	

	@Transient
	public boolean isChosir() {
		return chosir;
	}

	public void setChosir(boolean chosir) {
		this.chosir = chosir;
	}

	@Transient
	public int getCoef() {
		return coef;
	}

	public void setCoef(int coef) {
		this.coef = coef;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idmatiere == null) ? 0 : idmatiere.hashCode());
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
		Matiere other = (Matiere) obj;
		if (idmatiere == null) {
			if (other.idmatiere != null)
				return false;
		} else if (!idmatiere.equals(other.idmatiere))
			return false;
		return true;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idniveau")
	public Niveau getNiveau() {
		return niveau;
	}

	public void setNiveau(Niveau niveau) {
		this.niveau = niveau;
	}

}

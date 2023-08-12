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
 * @author a626257
 *
 */
@Entity
@Table(name="classe")
public class Classe implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idclasse;
	
	private String libelle;
	
	private Niveau niveau;
	
	private Double droit_ins;
	
	private Double mensualite;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idniveau")
	public Niveau getNiveau() {
		return niveau;
	}

	public void setNiveau(Niveau niveau) {
		this.niveau = niveau;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	@Id
	@GeneratedValue
	public Long getIdclasse() {
		return idclasse;
	}

	public void setIdclasse(Long idclasse) {
		this.idclasse = idclasse;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idclasse == null) ? 0 : idclasse.hashCode());
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
		Classe other = (Classe) obj;
		if (idclasse == null) {
			if (other.idclasse != null)
				return false;
		} else if (!idclasse.equals(other.idclasse))
			return false;
		return true;
	}

	public Double getDroit_ins() {
		return droit_ins;
	}

	public void setDroit_ins(Double droit_ins) {
		this.droit_ins = droit_ins;
	}

	public Double getMensualite() {
		return mensualite;
	}

	public void setMensualite(Double mensualite) {
		this.mensualite = mensualite;
	}

}

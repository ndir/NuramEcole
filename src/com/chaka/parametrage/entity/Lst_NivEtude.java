
package com.chaka.parametrage.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.Length;

import com.chaka.constantes.Constantes;


/**
 * 
 * @author 
 *
 */
@Entity
//@Name("NivEtude")
@Table(name="lst_NivEtude")
public class Lst_NivEtude implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3463892148915777622L;

	/**
	 * identifiant Taxes.
	 */
	private Long idNivEtude;
	/**
	 * libellé.
	 */
	@Length(max = Constantes.LONGUEUR_LIBELLE, message="X")
	private String libelle;
	/**
	 * libellé court.
	 */
	@Length(max = Constantes.LONGUEUR_LIBELLE_COURT, message="X")
	private String libelle_court;
	/**
	 * Date de dernière creation Langue.
	 */
	private Date dateCreation;

	/**
	 * Date de dernière modification Langue.
	 */
	private Date dateMaj;
	

	
	@Id
	@GeneratedValue
	public Long getIdNivEtude() {
		return idNivEtude;
	}

	public void setIdNivEtude(Long idNivEtude) {
		this.idNivEtude = idNivEtude;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle_court() {
		return libelle_court;
	}

	public void setLibelle_court(String libelle_court) {
		this.libelle_court = libelle_court;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Date getDateMaj() {
		return dateMaj;
	}

	public void setDateMaj(Date dateMaj) {
		this.dateMaj = dateMaj;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idNivEtude == null) ? 0 : idNivEtude.hashCode());
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
		Lst_NivEtude other = (Lst_NivEtude) obj;
		if (idNivEtude == null) {
			if (other.idNivEtude != null)
				return false;
		} else if (!idNivEtude.equals(other.idNivEtude))
			return false;
		return true;
	}

	
	
	
}

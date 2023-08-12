
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
//@Name("Service")
@Table(name="lst_Service")

public class Lst_Service implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3463892148915777622L;

	/**
	 * identifiant Taxes.
	 */
	private Long idService;
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
	public Long getIdService() {
		return idService;
	}

	public void setIdService(Long idService) {
		this.idService = idService;
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
				+ ((idService == null) ? 0 : idService.hashCode());
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
		Lst_Service other = (Lst_Service) obj;
		if (idService == null) {
			if (other.idService != null)
				return false;
		} else if (!idService.equals(other.idService))
			return false;
		return true;
	}

	
	
	
}

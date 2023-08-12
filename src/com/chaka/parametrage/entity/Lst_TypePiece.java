/**

 * @author Dell
 *
 */
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
 * @author owatt
 *
 */
@Entity
@Table(name="lst_TypePiece")
public class Lst_TypePiece implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3463892148915777622L;
	/**
	 * identifiant Taxes.
	 */
	private Long idTypePiece;
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
	public Long getIdTypePiece() {
		return idTypePiece;
	}

	public void setIdTypePiece(Long idTypePiece) {
		this.idTypePiece = idTypePiece;
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
				+ ((idTypePiece == null) ? 0 : idTypePiece.hashCode());
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
		Lst_TypePiece other = (Lst_TypePiece) obj;
		if (idTypePiece == null) {
			if (other.idTypePiece != null)
				return false;
		} else if (!idTypePiece.equals(other.idTypePiece))
			return false;
		return true;
	}

	
	
	
}

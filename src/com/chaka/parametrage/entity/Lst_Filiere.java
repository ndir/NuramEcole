/**
 * 
 */
package com.chaka.parametrage.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.Length;

import com.chaka.constantes.Constantes;

/**
 * @author a626257
 *
 */
@Entity
@Table
public class Lst_Filiere implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idFiliere;
	
	
	private String libelle;
	
	private String libelleCourt;
	
	private Date dateCreation;

	@Id
	@GeneratedValue
	public Long getIdFiliere() {
		return idFiliere;
	}

	public void setIdFiliere(Long idFiliere) {
		this.idFiliere = idFiliere;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	@Length(max = Constantes.LONGUEUR_LIBELLE_COURT, message="X")
	public String getLibelleCourt() {
		return libelleCourt;
	}

	public void setLibelleCourt(String libelleCourt) {
		this.libelleCourt = libelleCourt;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

}

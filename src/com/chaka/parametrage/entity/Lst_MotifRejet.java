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

import org.hibernate.validator.Length;

import com.chaka.constantes.Constantes;



@Entity
@Table(name="lst_motifRejet")
public class Lst_MotifRejet implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7501601236908319478L;


	/**
	 * identifiant Motif.
	 */
	private Long idMotif;
	/**
	 * Commentaire concernant la position.
	 */
	private String commentaire;
	
	/**
	 * libellé de la position.
	 */
	@Length(max = Constantes.LONGUEUR_LIBELLE, message="X")
	private String libelle;
	
	/**
	 * libellé court de la position.
	 */
	@Length(max = Constantes.LONGUEUR_LIBELLE_COURT, message="X")
	private String libelle_court;
	
	/**
	 * Date de la dernière creation de la position.
	 */
	private Date dateCreation;

	/**
	 * Date de la dernière modification de la position.
	 */
	private Date dateMaj;

	@Id
	@GeneratedValue
	public Long getIdMotif() {
		return idMotif;
	}

	public void setIdMotif(Long idMotif) {
		this.idMotif = idMotif;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
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
		result = prime * result + ((idMotif == null) ? 0 : idMotif.hashCode());
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
		Lst_MotifRejet other = (Lst_MotifRejet) obj;
		if (idMotif == null) {
			if (other.idMotif != null)
				return false;
		} else if (!idMotif.equals(other.idMotif))
			return false;
		return true;
	}
	
	

	
	
}

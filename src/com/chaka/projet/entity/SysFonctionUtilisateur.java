/**
 * @author Agnounty
 *
 */
package com.chaka.projet.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.validator.Length;
import com.chaka.constantes.Constantes;


@Entity
@Table(name="sys_fonctionUtilisateur")
public class SysFonctionUtilisateur implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3463892148915777622L;

	/**
	 * identifiant fonctionUtilisateur.
	 */
	private Long idFonctionUtilisateur;
	/**
	 * Commentaire concernant fonctionUtilisateur.
	 */
	private String commentaire;
	
	/**
	 * libellé du centre.
	 */
	private String libelle;
	
	/**
	 * libellé court du centre.
	 */
	private String code;
	
	/**
	 * Date de la dernière creation d'un centre.
	 */
	private Date dateCreation;

	/**
	 * Date de la dernière modification d'un centre.
	 */
	private Date dateMaj;
	/**
	 * utilsateur createur.
	 */
	private Utilisateur utilisateur;
	/**
	 * utilisateur qui a modifier le centre
	 */
	private Utilisateur utilisateurModif;
	
	/**
	 * methode construteur de la classe centre.
	 */
	public SysFonctionUtilisateur()
	{
		
	}
    
	/**
	 * @return the idCentre
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "seq_fonctionUtilisateur")  
	@SequenceGenerator(name="seq_fonctionUtilisateur", sequenceName = "seq_fonctionUtilisateur", allocationSize=1)
	public Long getIdFonctionUtilisateur() {
		return idFonctionUtilisateur;
	}

	public void setIdFonctionUtilisateur(Long idFonctionUtilisateur) {
		this.idFonctionUtilisateur = idFonctionUtilisateur;
	}

	/**
	 * @return the libelle.
	 */
	@Length(max = Constantes.LONGUEUR_LIBELLE, message="X")
	@Column(length = Constantes.LONGUEUR_LIBELLE)
	public String getLibelle() {
		return libelle;
	}


	/**
	 * @param libelle the libelle to set
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}


	/**
	 * @return the libelle_court
	 */
	@Length(max = Constantes.LONGUEUR_LIBELLE_COURT, message="X")
	@Column(length = Constantes.LONGUEUR_LIBELLE_COURT)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}



	/**
	 * @return the commentaire
	 */
	@Length(max = Constantes.LONGUEUR_COMMENTAIRE, message="X")
	@Column(length = Constantes.LONGUEUR_COMMENTAIRE)
	public String getCommentaire() {
		return commentaire;
	}


	/**
	 * @param commentaire the commentaire to set
	 */
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}


	/**
	 * @return the dateCreation
	 */
	public Date getDateCreation() {
		return dateCreation;
	}


	/**
	 * @param dateCreation the dateCreation to set
	 */
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}


	/**
	 * @return the dateMaj
	 */
	public Date getDateMaj() {
		return dateMaj;
	}


	/**
	 * @param dateMaj the dateMaj to set
	 */
	public void setDateMaj(Date dateMaj) {
		this.dateMaj = dateMaj;
	}

	/**
	 * @return the utilisateur
	 */
	@ManyToOne
	@JoinColumn(name = "idUtilisateur", nullable = false)
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	/**
	 * @param utilisateur the utilisateur to set
	 */
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	
	
	/**
	 * @return the utilisateur
	 */
	@ManyToOne
	@JoinColumn(name = "idUtilisateurModif", nullable = true)
	public Utilisateur getUtilisateurModif() {
		return utilisateurModif;
	}

	public void setUtilisateurModif(Utilisateur utilisateurModif) {
		this.utilisateurModif = utilisateurModif;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((idFonctionUtilisateur == null) ? 0
						: idFonctionUtilisateur.hashCode());
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
		SysFonctionUtilisateur other = (SysFonctionUtilisateur) obj;
		if (idFonctionUtilisateur == null) {
			if (other.idFonctionUtilisateur != null)
				return false;
		} else if (!idFonctionUtilisateur.equals(other.idFonctionUtilisateur))
			return false;
		return true;
	}

	
}

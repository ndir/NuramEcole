package com.ecole.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.chaka.projet.entity.Utilisateur;

@Entity
@Table(name = "recette")
public class Recette implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idRecette;
	private Inscription inscription;
	private TypeRecette typeRecette;
	private double montantPaye = 0;
	private Date datePaiment;
	private int[] mois;
	private int moisPaye;
	private boolean editable = false;

	private AnneeScolaire annee;

	private String commentaire;
	
	private Double montant;
	
	/**
	 * Utilisateur loggué
	 */
	private Utilisateur utilisateur;
	
	
	private Double avoirUtilise;
	
	private Institution institution;

	@Id
	@GeneratedValue
	public Long getIdRecette() {
		return idRecette;
	}

	public void setIdRecette(Long idRecette) {
		this.idRecette = idRecette;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idInscription")
	public Inscription getInscription() {
		return inscription;
	}

	public void setInscription(Inscription inscription) {
		this.inscription = inscription;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idTypeRecette")
	public TypeRecette getTypeRecette() {
		return typeRecette;
	}

	public void setTypeRecette(TypeRecette typeRecette) {
		this.typeRecette = typeRecette;
	}

	public double getMontantPaye() {
		return montantPaye;
	}

	public void setMontantPaye(double montantPaye) {
		this.montantPaye = montantPaye;
	}

	@Temporal(TemporalType.DATE)
	public Date getDatePaiment() {
		return datePaiment;
	}

	public void setDatePaiment(Date datePaiment) {
		this.datePaiment = datePaiment;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idUtilisateur")
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((datePaiment == null) ? 0 : datePaiment.hashCode());
		result = prime * result + ((idRecette == null) ? 0 : idRecette.hashCode());
		result = prime * result + ((inscription == null) ? 0 : inscription.hashCode());
		result = prime * result + Arrays.hashCode(mois);
		result = prime * result + moisPaye;
		long temp;
		temp = Double.doubleToLongBits(montantPaye);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((typeRecette == null) ? 0 : typeRecette.hashCode());
		result = prime * result + ((utilisateur == null) ? 0 : utilisateur.hashCode());
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
		Recette other = (Recette) obj;
		if (datePaiment == null) {
			if (other.datePaiment != null)
				return false;
		} else if (!datePaiment.equals(other.datePaiment))
			return false;
		if (idRecette == null) {
			if (other.idRecette != null)
				return false;
		} else if (!idRecette.equals(other.idRecette))
			return false;
		if (inscription == null) {
			if (other.inscription != null)
				return false;
		} else if (!inscription.equals(other.inscription))
			return false;
		if (!Arrays.equals(mois, other.mois))
			return false;
		if (moisPaye != other.moisPaye)
			return false;
		if (Double.doubleToLongBits(montantPaye) != Double.doubleToLongBits(other.montantPaye))
			return false;
		if (typeRecette == null) {
			if (other.typeRecette != null)
				return false;
		} else if (!typeRecette.equals(other.typeRecette))
			return false;
		if (utilisateur == null) {
			if (other.utilisateur != null)
				return false;
		} else if (!utilisateur.equals(other.utilisateur))
			return false;
		return true;
	}

	public int[] getMois() {
		return mois;
	}

	public void setMois(int[] mois) {
		this.mois = mois;
	}

	public int getMoisPaye() {
		return moisPaye;
	}

	public void setMoisPaye(int moisPaye) {
		this.moisPaye = moisPaye;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idannee")
	public AnneeScolaire getAnnee() {
		return annee;
	}

	public void setAnnee(AnneeScolaire annee) {
		this.annee = annee;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	@Transient
	public Double getMontant() {
		return montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}

	public Double getAvoirUtilise() {
		return avoirUtilise;
	}

	public void setAvoirUtilise(Double avoirUtilise) {
		this.avoirUtilise = avoirUtilise;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idinstitution")
	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	

}

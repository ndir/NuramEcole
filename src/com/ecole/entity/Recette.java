package com.ecole.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Out;

import com.chaka.projet.entity.Utilisateur;

@Entity
@Table(name = "recette")
public class Recette implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idRecette;
	private Inscription inscription;
	private TypeRecette typeRecette;
	private Double montantPaye;
	private Date datePaiment;
	
	/**
	 * Utilisateur loggué
	 */
	private Utilisateur utilisateur;
	
	@Id
	@GeneratedValue
	public Long getIdRecette() {
		return idRecette;
	}

	public void setIdRecette(Long idRecette) {
		this.idRecette = idRecette;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idInscription")
	public Inscription getInscription() {
		return inscription;
	}

	public void setInscription(Inscription inscription) {
		this.inscription = inscription;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idTypeRecette")
	public TypeRecette getTypeRecette() {
		return typeRecette;
	}

	public void setTypeRecette(TypeRecette typeRecette) {
		this.typeRecette = typeRecette;
	}

	public Double getMontantPaye() {
		return montantPaye;
	}

	public void setMontantPaye(Double montantPaye) {
		this.montantPaye = montantPaye;
	}

	public Date getDatePaiment() {
		return datePaiment;
	}

	public void setDatePaiment(Date datePaiment) {
		this.datePaiment = datePaiment;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idUtilisateur")
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
		result = prime * result + ((montantPaye == null) ? 0 : montantPaye.hashCode());
		result = prime * result + ((typeRecette == null) ? 0 : typeRecette.hashCode());
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
		if (montantPaye == null) {
			if (other.montantPaye != null)
				return false;
		} else if (!montantPaye.equals(other.montantPaye))
			return false;
		if (typeRecette == null) {
			if (other.typeRecette != null)
				return false;
		} else if (!typeRecette.equals(other.typeRecette))
			return false;
		return true;
	}
	

	
}

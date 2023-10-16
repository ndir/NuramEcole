package com.ecole.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.chaka.projet.entity.Utilisateur;

@Entity
@Table(name = "depense")
public class Depense implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idDepense;
	private TypeDepense typeDepense;
	private double montantPaye =0;
	private Date dateDepense;
	private int moisPaye;
	private boolean editable=false;
	private AnneeScolaire annee;
	
	private String commentaire;
	
	
	
	/**
	 * Utilisateur loggué
	 */
	private Utilisateur utilisateur;
	
	
	@Id
	@GeneratedValue
	public Long getIdDepense() {
		return idDepense;
	}
	public void setIdDepense(Long idDepense) {
		this.idDepense = idDepense;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idTypeDepense")
	public TypeDepense getTypeDepense() {
		return typeDepense;
	}
	public void setTypeDepense(TypeDepense typeDepense) {
		this.typeDepense = typeDepense;
	}
	
	@Temporal(TemporalType.DATE)
	public Date getDateDepense() {
		return dateDepense;
	}
	public void setDateDepense(Date dateDepense) {
		this.dateDepense = dateDepense;
	}


	public double getMontantPaye() {
		return montantPaye;
	}

	public void setMontantPaye(double montantPaye) {
		this.montantPaye = montantPaye;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idUtilisateur")
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
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
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idannee")
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

	
	
}

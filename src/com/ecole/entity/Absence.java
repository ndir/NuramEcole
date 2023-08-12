/**
 * 
 */
package com.ecole.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "absences")
public class Absence implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idAbsence;

	private Date dateAbsence;

	private Eleve eleve;

	private String heure;
	
	private MatiereClasse matiereClasse;

	@Id
	@GeneratedValue
	public Long getIdAbsence() {
		return idAbsence;
	}

	public void setIdAbsence(Long idAbsence) {
		this.idAbsence = idAbsence;
	}

	public Date getDateAbsence() {
		return dateAbsence;
	}

	public void setDateAbsence(Date dateAbsence) {
		this.dateAbsence = dateAbsence;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ideleve")
	public Eleve getEleve() {
		return eleve;
	}

	public void setEleve(Eleve eleve) {
		this.eleve = eleve;
	}

	public String getHeure() {
		return heure;
	}

	public void setHeure(String heure) {
		this.heure = heure;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idmatclasse")
	public MatiereClasse getMatiereClasse() {
		return matiereClasse;
	}

	public void setMatiereClasse(MatiereClasse matiereClasse) {
		this.matiereClasse = matiereClasse;
	}

}
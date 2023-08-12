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
@Table(name="retard")
public class Retard  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idRetard;
	private Date dateRetard;
	private MatiereClasse matiereClasse;
	private String heures;
	private Eleve eleve;
	@Id
	@GeneratedValue
	public Long getIdRetard() {
		return idRetard;
	}
	public void setIdRetard(Long idRetard) {
		this.idRetard = idRetard;
	}
	public Date getDateRetard() {
		return dateRetard;
	}
	public void setDateRetard(Date dateRetard) {
		this.dateRetard = dateRetard;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idmatclasse")
	public MatiereClasse getMatiereClasse() {
		return matiereClasse;
	}
	public void setMatiereClasse(MatiereClasse matiereClasse) {
		this.matiereClasse = matiereClasse;
	}
	public String getHeures() {
		return heures;
	}
	public void setHeures(String heures) {
		this.heures = heures;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ideleve")
	public Eleve getEleve() {
		return eleve;
	}
	public void setEleve(Eleve eleve) {
		this.eleve = eleve;
	}

}

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
	private Matiere matiere;
	private int heures;
	private Eleve eleve;
	
	private AnneeScolaire annee;
	
	private Semestres semestre;
	
	
	
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
	
	public int getHeures() {
		return heures;
	}
	public void setHeures(int heures) {
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
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idmatiere")
	public Matiere getMatiere() {
		return matiere;
	}
	public void setMatiere(Matiere matiere) {
		this.matiere = matiere;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idannee")
	public AnneeScolaire getAnnee() {
		return annee;
	}
	public void setAnnee(AnneeScolaire annee) {
		this.annee = annee;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idsmestre")
	public Semestres getSemestre() {
		return semestre;
	}
	public void setSemestre(Semestres semestre) {
		this.semestre = semestre;
	}

}

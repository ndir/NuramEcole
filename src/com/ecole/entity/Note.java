package com.ecole.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "note")
public class Note implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idNote;
	private String appreciation;
	private Eleve eleve;
	private MatiereClasse matiereClasse;	
	private float note;
	private String semestre;
	private Evaluation evaluation;
	@Id
	@GeneratedValue
	public Long getIdNote() {
		return idNote;
	}

	public void setIdNote(Long idNote) {
		this.idNote = idNote;
	}

	public float getNote() {
		return note;
	}

	public void setNote(float note) {
		this.note = note;
	}

	public String getAppreciation() {
		return appreciation;
	}

	public void setAppreciation(String appreciation) {
		this.appreciation = appreciation;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idEleve")
	public Eleve getEleve() {
		return eleve;
	}

	public void setEleve(Eleve eleve) {
		this.eleve = eleve;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idMatiere")
	public MatiereClasse getMatiereClasse() {
		return matiereClasse;
	}

	public void setMatiereClasse(MatiereClasse matiereClasse) {
		this.matiereClasse = matiereClasse;
	}
	
	public String getSemestre() {
		return semestre;
	}

	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idEvaluation")
	public Evaluation getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}
}

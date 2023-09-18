/**
 * 
 */
package com.ecole.entity;

import java.io.Serializable;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author A626257
 *
 */
public class DeliberationMS implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Eleve eleve;
	
	private Matiere matiere;
	
	private String moyenneD;

	private String moyenneC;
	
	private int coef;
	
	private int rang;
	
	private Appreciation appreciation;
	
	private String total;
	
	private int totalcoef;
	
	private String ranggen;
	
	private float absence;
	
	private float retard;
	
	private String app;
	
	private AnneeScolaire annee;

	@Id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ideleve")
	public Eleve getEleve() {
		return eleve;
	}

	public void setEleve(Eleve eleve) {
		this.eleve = eleve;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idmatiere")
	public Matiere getMatiere() {
		return matiere;
	}

	public void setMatiere(Matiere matiere) {
		this.matiere = matiere;
	}

	public String getMoyenneD() {
		return moyenneD;
	}

	public void setMoyenneD(String moyenneD) {
		this.moyenneD = moyenneD;
	}

	public String getMoyenneC() {
		return moyenneC;
	}

	public void setMoyenneC(String moyenneC) {
		this.moyenneC = moyenneC;
	}

	public int getCoef() {
		return coef;
	}

	public void setCoef(int coef) {
		this.coef = coef;
	}

	public int getRang() {
		return rang;
	}

	public void setRang(int rang) {
		this.rang = rang;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idappreciation")
	public Appreciation getAppreciation() {
		return appreciation;
	}

	public void setAppreciation(Appreciation appreciation) {
		this.appreciation = appreciation;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public int getTotalcoef() {
		return totalcoef;
	}

	public void setTotalcoef(int totalcoef) {
		this.totalcoef = totalcoef;
	}

	public String getRanggen() {
		return ranggen;
	}

	public void setRanggen(String ranggen) {
		this.ranggen = ranggen;
	}

	public float getAbsence() {
		return absence;
	}

	public void setAbsence(float absence) {
		this.absence = absence;
	}

	public float getRetard() {
		return retard;
	}

	public void setRetard(float retard) {
		this.retard = retard;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idannee")
	public AnneeScolaire getAnnee() {
		return annee;
	}

	public void setAnnee(AnneeScolaire annee) {
		this.annee = annee;
	}

}

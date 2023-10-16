/**
 * 
 */
package com.ecole.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author A626257
 *
 */
@Entity
@Table(name = "deliberationfinal")
public class DeliberationFinal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private Double moy1;

	private String moy1s;

	private Double moy2;

	private String moy2s;

	private Double moy3;

	private String moy3s;

	private String rang1;

	private String rang2;

	private String rang3;

	private String ap1;

	private String ap2;

	private String ap3;

	private Eleve eleve;
	
	private Double moyan;
	
	private String moyans;

	private AnneeScolaire annee;
	
	private String rangan;
	private String apa;
	private String decision;
	
	private Classe classe;
	
	private String use;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getMoy1() {
		return moy1;
	}

	public void setMoy1(Double moy1) {
		this.moy1 = moy1;
	}

	public String getMoy1s() {
		return moy1s;
	}

	public void setMoy1s(String moy1s) {
		this.moy1s = moy1s;
	}

	public Double getMoy2() {
		return moy2;
	}

	public void setMoy2(Double moy2) {
		this.moy2 = moy2;
	}

	public String getMoy2s() {
		return moy2s;
	}

	public void setMoy2s(String moy2s) {
		this.moy2s = moy2s;
	}

	public Double getMoy3() {
		return moy3;
	}

	public void setMoy3(Double moy3) {
		this.moy3 = moy3;
	}

	public String getMoy3s() {
		return moy3s;
	}

	public void setMoy3s(String moy3s) {
		this.moy3s = moy3s;
	}

	public String getRang1() {
		return rang1;
	}

	public void setRang1(String rang1) {
		this.rang1 = rang1;
	}

	public String getRang2() {
		return rang2;
	}

	public void setRang2(String rang2) {
		this.rang2 = rang2;
	}

	public String getRang3() {
		return rang3;
	}

	public void setRang3(String rang3) {
		this.rang3 = rang3;
	}

	public String getAp1() {
		return ap1;
	}

	public void setAp1(String ap1) {
		this.ap1 = ap1;
	}

	public String getAp2() {
		return ap2;
	}

	public void setAp2(String ap2) {
		this.ap2 = ap2;
	}

	public String getAp3() {
		return ap3;
	}

	public void setAp3(String ap3) {
		this.ap3 = ap3;
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
	@JoinColumn(name = "idannee")
	public AnneeScolaire getAnnee() {
		return annee;
	}

	public void setAnnee(AnneeScolaire annee) {
		this.annee = annee;
	}

	public Double getMoyan() {
		return moyan;
	}

	public void setMoyan(Double moyan) {
		this.moyan = moyan;
	}

	public String getMoyans() {
		return moyans;
	}

	public void setMoyans(String moyans) {
		this.moyans = moyans;
	}

	public String getApa() {
		return apa;
	}

	public void setApa(String apa) {
		this.apa = apa;
	}

	public String getRangan() {
		return rangan;
	}

	public void setRangan(String rangan) {
		this.rangan = rangan;
	}

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idclasse")
	public Classe getClasse() {
		return classe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}

	@Transient
	public String getUse() {
		return use;
	}

	public void setUse(String use) {
		this.use = use;
	}

}

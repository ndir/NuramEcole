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

/**
 * @author a626257
 * 
 */
@Entity
@Table(name = "eleve")
public class Eleve implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long ideleve;

	private String nom;

	private String prenom;

	private String titeur;

	private String telephone;

	private Classe classe;

	private String annee_ins;

	private String adresse;

	private int age;

	private Date dateNaissance;
	
	private String nationalite;
	
	private Double reduction;
	
	private String autre_info;
	
	private String numero;
	
	private String sexe;
	
	@Id
	@GeneratedValue
	public Long getIdeleve() {
		return ideleve;
	}

	public void setIdeleve(Long ideleve) {
		this.ideleve = ideleve;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getTiteur() {
		return titeur;
	}

	public void setTiteur(String titeur) {
		this.titeur = titeur;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idclasse")
	public Classe getClasse() {
		return classe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}

	public String getAnnee_ins() {
		return annee_ins;
	}

	public void setAnnee_ins(String annee_ins) {
		this.annee_ins = annee_ins;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ideleve == null) ? 0 : ideleve.hashCode());
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
		Eleve other = (Eleve) obj;
		if (ideleve == null) {
			if (other.ideleve != null)
				return false;
		} else if (!ideleve.equals(other.ideleve))
			return false;
		return true;
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getNationalite() {
		return nationalite;
	}

	public void setNationalite(String nationalite) {
		this.nationalite = nationalite;
	}

	public Double getReduction() {
		return reduction;
	}

	public void setReduction(Double reduction) {
		this.reduction = reduction;
	}

	public String getAutre_info() {
		return autre_info;
	}

	public void setAutre_info(String autre_info) {
		this.autre_info = autre_info;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

}

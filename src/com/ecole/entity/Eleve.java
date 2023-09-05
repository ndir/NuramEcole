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
import javax.persistence.Transient;

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

	private String adresse;

	private int age;

	private Date dateNaissance;
	
	private String nationalite;
	
	
	
	private String autre_info;
	
	private String numero;
	
	private String sexe;
	
	private float note;
	
	
	private boolean choix;
	
	private boolean existe;
	
	private Long idNote;
	
	private Long idMat;
	
	private int coef;
	
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

	@Transient
	public float getNote() {
		return note;
	}

	public void setNote(float note) {
		this.note = note;
	}

	@Transient
	public boolean isChoix() {
		return choix;
	}

	public void setChoix(boolean choix) {
		this.choix = choix;
	}

	@Transient
	public boolean isExiste() {
		return existe;
	}

	public void setExiste(boolean existe) {
		this.existe = existe;
	}

	@Transient
	public Long getIdNote() {
		return idNote;
	}

	public void setIdNote(Long idNote) {
		this.idNote = idNote;
	}

	@Transient
	public Long getIdMat() {
		return idMat;
	}

	public void setIdMat(Long idMat) {
		this.idMat = idMat;
	}

	@Transient
	public int getCoef() {
		return coef;
	}

	public void setCoef(int coef) {
		this.coef = coef;
	}

}

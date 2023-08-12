
package com.chaka.parametrage.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.Length;

import com.chaka.constantes.Constantes;


/**
 * 
 * @author 
 *
 */
@Entity
//@Name("Sexe")
@Table(name="personnel")

public class Personnel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3463892148915777622L;

	/**
	 * identifiant Taxes.
	 */
	private Long idPersonnel;
	/**
	 * libellé.
	 */
	@Length(max = Constantes.LONGUEUR_LIBELLE, message="X")
	private String prenom;
	
	@Length(max = 20, message="X")
	private String nom;
	
	private Date dateNaissance;
	
	@Length(max = 80, message="X")
	private String lieuNaissance;
	
	@Length(max = 20, message="X")
	private String matricule;
	
	
	private int age;
	
	private Lst_CategoriProf categoriProg;
	
	private Lst_TypeContrat contrant;
	
	private Lst_Statut statut;
	
	private Lst_Sexe sexe;
	
	private Lst_SituationMatri situationMatri;
	
	private Lst_Service service;
	
	private Lst_NivEtude nivEtude;

	@Id
	@GeneratedValue
	public Long getIdPersonnel() {
		return idPersonnel;
	}

	public void setIdPersonnel(Long idPersonnel) {
		this.idPersonnel = idPersonnel;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getLieuNaissance() {
		return lieuNaissance;
	}

	public void setLieuNaissance(String lieuNaissance) {
		this.lieuNaissance = lieuNaissance;
	}

	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCategoriProg", nullable = true)
	public Lst_CategoriProf getCategoriProg() {
		return categoriProg;
	}

	public void setCategoriProg(Lst_CategoriProf categoriProg) {
		this.categoriProg = categoriProg;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idTypeContrat", nullable = true)
	public Lst_TypeContrat getContrant() {
		return contrant;
	}

	public void setContrant(Lst_TypeContrat contrant) {
		this.contrant = contrant;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idStatut", nullable = true)
	public Lst_Statut getStatut() {
		return statut;
	}

	public void setStatut(Lst_Statut statut) {
		this.statut = statut;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idSexe", nullable = true)
	public Lst_Sexe getSexe() {
		return sexe;
	}

	public void setSexe(Lst_Sexe sexe) {
		this.sexe = sexe;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idSituationMatri", nullable = true)
	public Lst_SituationMatri getSituationMatri() {
		return situationMatri;
	}

	public void setSituationMatri(Lst_SituationMatri situationMatri) {
		this.situationMatri = situationMatri;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idService", nullable = true)
	public Lst_Service getService() {
		return service;
	}

	public void setService(Lst_Service service) {
		this.service = service;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idNivEtude", nullable = true)
	public Lst_NivEtude getNivEtude() {
		return nivEtude;
	}

	public void setNivEtude(Lst_NivEtude nivEtude) {
		this.nivEtude = nivEtude;
	}
	
	
	
	
}
	
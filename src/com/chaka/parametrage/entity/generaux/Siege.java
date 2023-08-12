/**
 * @author Dell
 *
 */
package com.chaka.parametrage.entity.generaux;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.Length;
import org.jboss.seam.annotations.Name;

import com.chaka.constantes.Constantes;
import com.chaka.projet.entity.Utilisateur;


/**
 * 
 * @author Souané
 *
 */
@Entity
@Name("siege")
@Table(name="siege_cnss")
public class Siege implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3463892148915777622L;

	/**
	 * identifiant siege.
	 */
	private Long idSiege;
	
    /**
     * identification siege.
     */
	@Length(max = 120, message="X")
	@Column(length = 120)
	private String identification;
	/**
	 * libellé court.
	 */
	@Length(max = Constantes.LONGUEUR_LIBELLE_COURT, message="X")
	@Column(length = Constantes.LONGUEUR_LIBELLE_COURT)
	private String libelle_court;
	/**
	 * adress du siège.
	 */
	@Length(max = Constantes.LONGUEUR_ADRESSE, message="X")
	@Column(length = Constantes.LONGUEUR_ADRESSE)
	private String adresse;
	/**
	 * ville ou réside le siege.
	 */
	@Length(max = Constantes.LONGUEUR_50, message="X")
	@Column(length = Constantes.LONGUEUR_50)
	private String ville;
	/**
	 * code postal du siege.
	 */
	@Length(max = 5)
	private String codePostal;
	/**
	 * numero de téléphone 1.
	 */
	@Length(max = Constantes.LONGUEUR_TELEPHONE, message="X")
	
	private String tel1;
	/**
	 * numero de téléphone 2.
	 */
	@Length(max = Constantes.LONGUEUR_TELEPHONE, message="X")
	
	private String tel2;
	/**
	 * numero de téléphone mobile.
	 */
	@Length(max = Constantes.LONGUEUR_TELEPHONE, message="X")
	
	private String telMobile;
	/**
	 * numero de fax.
	 */
	@Length(max = Constantes.LONGUEUR_TELEPHONE, message="X")
	
	private String fax;
	/**
	 * Pays du siege.
	 */
	@Length(max = Constantes.LONGUEUR_50, message="X")
	@Column(length = Constantes.LONGUEUR_50)
	private String pays;
	/**
	 * email 1 du siege.
	 */
	@Length(max = Constantes.LONGUEUR_50, message="X")
	@Column(length = Constantes.LONGUEUR_50)
	private String email1;
	/**
	 * email 2 du siege.
	 */
	@Length(max = Constantes.LONGUEUR_50, message="X")
	@Column(length = Constantes.LONGUEUR_50)
	private String email2;
	/**
	 * Date début exercice du siege.
	 */
	@Temporal(TemporalType.DATE)
	private Date dateDebutExercice;
	/**
	 * Date fin exercice du siege.
	 */
	@Temporal(TemporalType.DATE)
	private Date dateFinExercice;
	/**
	 * Deate de creation du siége.
	 */
	
	private Date dateCreation;

	/**
	 * Date de dernière modification du siége.
	 */
	private Date dateMaj;
	
	// Paritie imatriculation
	
	/**
	 * Matricule fiscal.
	 */
	@Length(max = Constantes.LONGUEUR_MATRICULE, message="X")
	private String matriculeFiscal;
	/**
	 * Matricule social.
	 */
	@Length(max = Constantes.LONGUEUR_MATRICULE, message="X")
	private String matriculeSocial;
	/**
	 * Matricule assurance.
	 */
	@Length(max = Constantes.LONGUEUR_MATRICULE, message="X")
	private String matriculeAssurance;
	/**
	 *Numero registre de commerce..
	 */
	@Length(max = Constantes.LONGUEUR_MATRICULE, message="X")
	private String registreCommerce;
	/**
	 * Date matricule fiscal.
	 */
	@Temporal(TemporalType.DATE)
	private Date dateMatFiscal;
	/**
	 * Date matricule social du siege.
	 */
	@Temporal(TemporalType.DATE)
	private Date dateMatSocial;
	/**
	 * Deate matricule assurance du siége.
	 */
	@Temporal(TemporalType.DATE)
	private Date dateMatAssurance;
	/**
	 * Date registre de commerce..
	 */
	@Temporal(TemporalType.DATE)
	private Date dateRegistreComm;
	/**
	 * Date Immatricultaion.
	 */
	@Temporal(TemporalType.DATE)
	private Date dateImmatriculation;
	/**
	 * liste monnaie.
	 */
	/**
	 * la langue du siege.
	 */
	private Langues langue;
	
	// les listes***************************
	/**
	 * liste niveaux administratifs.
	 */
	private List<NiveauAdministratif> listNiveauAdmin;
	/**
	 * liste niveaux hierarchique.
	 */
	private List<NiveauHierarchique> listNiveauHiera;
	/**
	 * liste etablissements.
	 */
	private List<Etablissement> listEtablissement;
	/**
	 *  utilisateur de saisie.
	 */
	private Utilisateur utilisateur;
	
	
	/**
	 * methode construteur de la classe siege.
	 */
	public Siege()
	{
		this.listEtablissement = new ArrayList<Etablissement>();
		this.listNiveauAdmin = new ArrayList<NiveauAdministratif>();
		this.listNiveauHiera = new ArrayList<NiveauHierarchique>();
	}

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue
	public Long getIdSiege() {
		return idSiege;
	}

	/**
	 * @param id the id to set
	 */
	public void setIdSiege(Long idSiege) {
		this.idSiege = idSiege;
	}

	/**
	 * @return the identification
	 */
	public String getIdentification() {
		return identification;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idSiege == null) ? 0 : idSiege.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Siege other = (Siege) obj;
		if (idSiege == null) {
			if (other.idSiege != null)
				return false;
		} else if (!idSiege.equals(other.idSiege))
			return false;
		return true;
	}

	/**
	 * @param identification the identification to set
	 */
	public void setIdentification(String identification) {
		this.identification = identification;
	}

	/**
	 * @return the libelle_court
	 */
	public String getLibelle_court() {
		return libelle_court;
	}

	/**
	 * @param libelle_court the libelle_court to set
	 */
	public void setLibelle_court(String libelle_court) {
		this.libelle_court = libelle_court;
	}

	/**
	 * @return the adresse
	 */
	public String getAdresse() {
		return adresse;
	}

	/**
	 * @param adresse the adresse to set
	 */
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	/**
	 * @return the ville
	 */
	public String getVille() {
		return ville;
	}

	/**
	 * @param ville the ville to set
	 */
	public void setVille(String ville) {
		this.ville = ville;
	}

	/**
	 * @return the tel1
	 */
	public String getTel1() {
		return tel1;
	}

	/**
	 * @param tel1 the tel1 to set
	 */
	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}

	/**
	 * @return the tel2
	 */
	public String getTel2() {
		return tel2;
	}

	/**
	 * @param tel2 the tel2 to set
	 */
	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}

	/**
	 * @return the telMobile
	 */
	public String getTelMobile() {
		return telMobile;
	}

	/**
	 * @param telMobile the telMobile to set
	 */
	public void setTelMobile(String telMobile) {
		this.telMobile = telMobile;
	}

	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @return the pays
	 */
	public String getPays() {
		return pays;
	}

	/**
	 * @param pays the pays to set
	 */
	public void setPays(String pays) {
		this.pays = pays;
	}

	/**
	 * @return the email1
	 */
	public String getEmail1() {
		return email1;
	}

	/**
	 * @param email1 the email1 to set
	 */
	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	/**
	 * @return the email2
	 */
	public String getEmail2() {
		return email2;
	}

	/**
	 * @param email2 the email2 to set
	 */
	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	/**
	 * @return the dateDebutExercice
	 */
	public Date getDateDebutExercice() {
		return dateDebutExercice;
	}

	/**
	 * @param dateDebutExercice the dateDebutExercice to set
	 */
	public void setDateDebutExercice(Date dateDebutExercice) {
		this.dateDebutExercice = dateDebutExercice;
	}

	/**
	 * @return the dateFinExercice
	 */
	public Date getDateFinExercice() {
		return dateFinExercice;
	}

	/**
	 * @param dateFinExercice the dateFinExercice to set
	 */
	public void setDateFinExercice(Date dateFinExercice) {
		this.dateFinExercice = dateFinExercice;
	}

	/**
	 * @return the dateCreation
	 */
	public Date getDateCreation() {
		return dateCreation;
	}

	/**
	 * @param dateCreation the dateCreation to set
	 */
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	/**
	 * @return the dateMaj
	 */
	public Date getDateMaj() {
		return dateMaj;
	}

	/**
	 * @param dateMaj the dateMaj to set
	 */
	public void setDateMaj(Date dateMaj) {
		this.dateMaj = dateMaj;
	}

	/**
	 * @return the matriculeFiscal
	 */
	public String getMatriculeFiscal() {
		return matriculeFiscal;
	}

	/**
	 * @param matriculeFiscal the matriculeFiscal to set
	 */
	public void setMatriculeFiscal(String matriculeFiscal) {
		this.matriculeFiscal = matriculeFiscal;
	}

	/**
	 * @return the matriculeSocial
	 */
	public String getMatriculeSocial() {
		return matriculeSocial;
	}

	/**
	 * @param matriculeSocial the matriculeSocial to set
	 */
	public void setMatriculeSocial(String matriculeSocial) {
		this.matriculeSocial = matriculeSocial;
	}

	/**
	 * @return the matriculeAssurance
	 */
	public String getMatriculeAssurance() {
		return matriculeAssurance;
	}

	/**
	 * @param matriculeAssurance the matriculeAssurance to set
	 */
	public void setMatriculeAssurance(String matriculeAssurance) {
		this.matriculeAssurance = matriculeAssurance;
	}

	/**
	 * @return the registreCommercce
	 */
	public String getRegistreCommerce() {
		return registreCommerce;
	}

	/**
	 * @param registreCommercce the registreCommercce to set
	 */
	public void setRegistreCommerce(String registreCommerce) {
		this.registreCommerce = registreCommerce;
	}

	/**
	 * @return the dateMatFiscal
	 */
	public Date getDateMatFiscal() {
		return dateMatFiscal;
	}

	/**
	 * @param dateMatFiscal the dateMatFiscal to set
	 */
	public void setDateMatFiscal(Date dateMatFiscal) {
		this.dateMatFiscal = dateMatFiscal;
	}

	/**
	 * @return the dateMatSocial
	 */
	public Date getDateMatSocial() {
		return dateMatSocial;
	}

	/**
	 * @param dateMatSocial the dateMatSocial to set
	 */
	public void setDateMatSocial(Date dateMatSocial) {
		this.dateMatSocial = dateMatSocial;
	}

	/**
	 * @return the dateMAtAssurance
	 */
	public Date getDateMatAssurance() {
		return dateMatAssurance;
	}

	/**
	 * @param dateMatAssurance the dateMatAssurance to set
	 */
	public void setDateMatAssurance(Date dateMatAssurance) {
		this.dateMatAssurance = dateMatAssurance;
	}

	/**
	 * @return the dateRegistreComm
	 */
	public Date getDateRegistreComm() {
		return dateRegistreComm;
	}

	/**
	 * @param dateRegistreComm the dateRegistreComm to set
	 */
	public void setDateRegistreComm(Date dateRegistreComm) {
		this.dateRegistreComm = dateRegistreComm;
	}

	/**
	 * @return the dateImmatriculation
	 */
	public Date getDateImmatriculation() {
		return dateImmatriculation;
	}

	/**
	 * @param dateImmatriculation the dateImmatriculation to set
	 */
	public void setDateImmatriculation(Date dateImmatriculation) {
		this.dateImmatriculation = dateImmatriculation;
	}


	/**
	 * @return the langue
	 */
	@ManyToOne
	@JoinColumn(name="idLangue", nullable=true)
	public Langues getLangue() {
		return langue;
	}

	/**
	 * @param langue the langue to set
	 */
	public void setLangue(Langues langue) {
		this.langue = langue;
	}

	/**
	 * @return the listeNiveauAdmin
	 */
	@OneToMany
	@JoinColumn(name = "idSiege",nullable=false)
	@Cascade(value={CascadeType.ALL,CascadeType.DELETE_ORPHAN})
	public List<NiveauAdministratif> getListNiveauAdmin() {
		return listNiveauAdmin;
	}

	/**
	 * @param listNiveauAdmin the listNiveauAdmin to set
	 */
	public void setListNiveauAdmin(List<NiveauAdministratif> listNiveauAdmin) {
		this.listNiveauAdmin = listNiveauAdmin;
	}

	/**
	 * @return the listeNiveauHiera
	 */
	@OneToMany
	@JoinColumn(name = "idSiege",nullable=false)
	@Cascade(value={CascadeType.ALL,CascadeType.DELETE_ORPHAN})
	public List<NiveauHierarchique> getListNiveauHiera() {
		return listNiveauHiera;
	}

	/**
	 * @param listNiveauHiera the listNiveauHiera to set
	 */
	public void setListNiveauHiera(List<NiveauHierarchique> listNiveauHiera) {
		this.listNiveauHiera = listNiveauHiera;
	}

	/**
	 * @return the listeEtablissement
	 */
	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumn(name = "idSiege",nullable=false)
	@Cascade(value={CascadeType.ALL,CascadeType.DELETE_ORPHAN})
	public List<Etablissement> getListEtablissement() {
		return listEtablissement;
	}

	/**
	 * @param listEtablissement the listEtablissement to set
	 */
	public void setListEtablissement(List<Etablissement> listEtablissement) {
		this.listEtablissement = listEtablissement;
	}

	/**
	 * @return the utilisateur
	 */
	@ManyToOne
	@JoinColumn(name = "idUtilisateur", nullable = false)
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	/**
	 * @param utilisateur the utilisateur to set
	 */
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	/**
	 * @return the codePostal
	 */
	public String getCodePostal() {
		return codePostal;
	}

	/**
	 * @param codePostal the codePostal to set
	 */
	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}
	
	
	
}

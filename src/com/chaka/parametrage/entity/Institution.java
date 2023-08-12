/**
 * 
 */
package com.chaka.parametrage.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;



/**
 * @author Serigne FALL
 *
 */
@Entity
public class Institution implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idInstitution;
	
	private String libelle ;
	
	private String libelle_court ;
	
	private String  adresseSociete;
	
	private String  telSociete;
	
	private String  faxeSociete;
	
	private String  bpSociete;
	
	private String emailSociete;
	
	private Boolean  recreatMenu=false;
	
	private String  cheminLogoSociete;
	
	private String  sloganSociete ;
	
	private Integer nbrConge ;
	
	private Boolean cacherMenuON=false;
	
	private Date dateMaj;
	
	private Date dateModification;

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue
	public Long getIdInstitution() {
		return idInstitution;
	}

	public void setIdInstitution(Long idInstitution) {
		this.idInstitution = idInstitution;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle_court() {
		return libelle_court;
	}

	public void setLibelle_court(String libelle_court) {
		this.libelle_court = libelle_court;
	}


	public Boolean getCacherMenuON() {
		return cacherMenuON;
	}

	public void setCacherMenuON(Boolean cacherMenuON) {
		this.cacherMenuON = cacherMenuON;
	}

	public String getAdresseSociete() {
		return adresseSociete;
	}

	public void setAdresseSociete(String adresseSociete) {
		this.adresseSociete = adresseSociete;
	}

	public String getTelSociete() {
		return telSociete;
	}

	public void setTelSociete(String telSociete) {
		this.telSociete = telSociete;
	}

	public String getBpSociete() {
		return bpSociete;
	}

	public void setBpSociete(String bpSociete) {
		this.bpSociete = bpSociete;
	}

	public String getEmailSociete() {
		return emailSociete;
	}

	public void setEmailSociete(String emailSociete) {
		this.emailSociete = emailSociete;
	}

	public Boolean getRecreatMenu() {
		return recreatMenu;
	}

	public void setRecreatMenu(Boolean recreatMenu) {
		this.recreatMenu = recreatMenu;
	}

	public String getCheminLogoSociete() {
		return cheminLogoSociete;
	}

	public void setCheminLogoSociete(String cheminLogoSociete) {
		this.cheminLogoSociete = cheminLogoSociete;
	}

	public String getFaxeSociete() {
		return faxeSociete;
	}

	public void setFaxeSociete(String faxeSociete) {
		this.faxeSociete = faxeSociete;
	}

	

	public String getSloganSociete() {
		return sloganSociete;
	}

	public void setSloganSociete(String sloganSociete) {
		this.sloganSociete = sloganSociete;
	}

	public Date getDateMaj() {
		return dateMaj;
	}

	public void setDateMaj(Date dateMaj) {
		this.dateMaj = dateMaj;
	}

	public Date getDateModification() {
		return dateModification;
	}

	public void setDateModification(Date dateModification) {
		this.dateModification = dateModification;
	}

	public Integer getNbrConge() {
		return nbrConge;
	}

	public void setNbrConge(Integer nbrConge) {
		this.nbrConge = nbrConge;
	}
	

	
	
	
	
	
	
	
}

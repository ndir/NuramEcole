/**
 * @author Dell
 *
 */
package com.chaka.parametrage.entity.generaux;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.Length;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import com.chaka.constantes.Constantes;




/**
 * 
 * @author Chaka
 *
 */
@Entity
@Name("etablissement")
@Scope(ScopeType.SESSION)
@Table(name="etablissement")
public class Etablissement implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3463892148915777622L;

	/**
	 * identifiant etablissement.
	 */
	private Long idEtablissement;
	/**
	 * code etavlissement.
	 */
	@Length(max = Constantes.LONGUEUR_CODE, message="X")
	@Column(length = Constantes.LONGUEUR_CODE)
	private String code;
	/**
	 * libellé court.
	 */
	@Length(max = Constantes.LONGUEUR_LIBELLE, message="X")
	@Column(length = Constantes.LONGUEUR_LIBELLE)
	private String libelle;
	/**
	 * adress du siège.
	 */
	@Length(max = Constantes.LONGUEUR_ADRESSE)
	@Column(length = Constantes.LONGUEUR_ADRESSE)
	private String adresse;
	/**
	 * numero de téléphone 1.
	 */
	@Length(max = Constantes.LONGUEUR_TELEPHONE, message="X")
	@Column(length = Constantes.LONGUEUR_TELEPHONE)
	private String tel1;
	/**
	 * numero de téléphone 2.
	 */
	@Length(max = Constantes.LONGUEUR_TELEPHONE, message="X")
	@Column(length = Constantes.LONGUEUR_TELEPHONE)
	private String tel2;
	/**
	 * numero de téléphone mobile.
	 */
	@Length(max = Constantes.LONGUEUR_TELEPHONE, message="X")
	@Column(length = Constantes.LONGUEUR_TELEPHONE)
	private String telMobile;
	/**
	 * numero de fax.
	 */
	@Length(max = Constantes.LONGUEUR_TELEPHONE, message="X")
	@Column(length = Constantes.LONGUEUR_TELEPHONE)
	private String fax;
	/**
	 * La personne à contacter de l'etablissement.
	 */
	@Length(max = Constantes.LONGUEUR_50, message="X")
	@Column(length = Constantes.LONGUEUR_50)
	private String personneContact;
	/**
	 * email 1 su etablissement.
	 */
	@Length(max = Constantes.LONGUEUR_50, message="X")
	@Column(length = Constantes.LONGUEUR_50)
	private String email1;
	/**
	 * email 2 su etablissement.
	 */
	@Length(max = Constantes.LONGUEUR_50, message="X")
	@Column(length = Constantes.LONGUEUR_50)
	private String email2;
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
	 * Nom responsable.
	 */
	@Length(max = Constantes.LONGUEUR_50, message="X")
	@Column(length = Constantes.LONGUEUR_50)
	private String nomResponsable;
	/**
	 * Zone commentaire.
	 */
	@Lob
	private String zoneComment;
	/**
	 * siege de l'etablissement.
	 */
	private Siege siege;
	/**
	 * centre impots.
	 */
	/**
	 * codepostal.
	 */
	//private CodePostaux codePostal;
	
	/**
	 * Clé utilisée lorsque l'entité n'est pas encore persistée.
	 */
	private Long idTransient;
	
	/**
	 * @return the idEtablissement
	 */
	@Id
	@GeneratedValue
	public Long getIdEtablissement() {
		return idEtablissement;
	}
	/**
	 * @param idEtablissement the idEtablissement to set
	 */
	public void setIdEtablissement(Long idEtablissement) {
		this.idEtablissement = idEtablissement;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the libelle
	 */
	public String getLibelle() {
		return libelle;
	}
	/**
	 * @param libelle the libelle to set
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
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
	 * @return the personneContact
	 */
	public String getPersonneContact() {
		return personneContact;
	}
	/**
	 * @param personneContact the personneContact to set
	 */
	public void setPersonneContact(String personneContact) {
		this.personneContact = personneContact;
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
	 * @return the nomResponsable
	 */
	public String getNomResponsable() {
		return nomResponsable;
	}
	/**
	 * @param nomResponsable the nomResponsable to set
	 */
	public void setNomResponsable(String nomResponsable) {
		this.nomResponsable = nomResponsable;
	}
	/**
	 * @return the zoneComment
	 */
	public String getZoneComment() {
		return zoneComment;
	}
	/**
	 * @param zoneComment the zoneComment to set
	 */
	public void setZoneComment(String zoneComment) {
		this.zoneComment = zoneComment;
	}
	/**
	 * @return the siege
	 */
	@ManyToOne
	@JoinColumn(name = "idSiege",insertable=false,updatable=false)
	public Siege getSiege() {
		return siege;
	}
	/**
	 * @param siege the siege to set
	 */
	public void setSiege(Siege siege) {
		this.siege = siege;
	}
	
	/**
	 * methode construteur de la classe etablissement.
	 */
	public Etablissement()
	{
		
	}
	
	
	
	/**
	 * @return the codePostal
	 */
	/*@ManyToOne
	@JoinColumn(name = "idCodePostal",insertable=false,updatable=false)
	public CodePostaux getCodePostal() {
		return codePostal;
	}
	/**
	 * @param codePostal the codePostal to set
	 */
/*	public void setCodePostal(CodePostaux codePostal) {
		this.codePostal = codePostal;
	}
	/**
	 * @return the idTransient
	 */
	@Transient
	public Long getIdTransient() {
		return idTransient;
	}
	/**
	 * @param idTransient the idTransient to set
	 */
	public void setIdTransient(Long idTransient) {
		this.idTransient = idTransient;
	}
	/**
	 * @return the codePostal
	 *//*
	@ManyToOne
	@JoinColumn(name="idCodePostaux", nullable=true)
	public CodePostaux getCodePostal() {
		return codePostal;
	}
	*//**
	 * @param codePostal the codePostal to set
	 *//*
	public void setCodePostal(CodePostaux codePostal) {
		this.codePostal = codePostal;
	}
	*/
	
	
}

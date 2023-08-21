package com.chaka.projet.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.Email;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Role;
import org.jboss.seam.annotations.Scope;

import com.ecole.entity.AnneeScolaire;
import com.ecole.entity.Institution;


/**
 * Classe de données representant un utilisateur dans l'application. Cette
 * classe est persistante via Hibernate. Elle est liée à la table UTILISATEUR.
 *
 * @author Chaka V 0.1 : Maj : 09/11/2007
 */
@Entity
@Table(name = "utilisateur")
@Name("utilisateur")
@Scope(ScopeType.SESSION)
@Role(name = "user", scope = ScopeType.CONVERSATION)
public class Utilisateur implements Serializable {
	
	private static final long serialVersionUID = 3006466330479401097L;
	
	

	public static final int ADMIN = 1;
	
	public static final int USER = 2;


	
	/**
	 * Constante representant le role administrateur
	 */
	public static final String R_ADMIN = "admin";
	
	public static final String R_USER = "user";
	
	
	
	private Long idUtilisateur;

	private Date dateCreation;

	private Date dateMaj;
	
	private String login;
	
	private String password;
	
	private String password2;
	
	private String nom;
	
	private String prenom;
	
	private String email;
	
	
	private Profile profile;
	
	private boolean actif;
	private boolean initPass;
	

	
	private String telephone;
	
	
	private String diplome_ac;
	
	private String diplome_p;
	
	
	
	/**
	 *  insertion souané
	 * @return
	 */

	
	
	

	public boolean isActif() {
		return actif;
	}

	public void setActif(boolean actif) {
		this.actif = actif;
	}

	
	@ManyToOne
	@JoinColumn(name = "idProfile")
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}


	

	@Column(unique=true)
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Transient
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	
	@Transient
	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	@Email(message="X")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Transient
	public String getNomCompletCourt() {
		return prenom +  " " + nom;
	}

	public void setNomCompletCourt(String nomCompletCourt) {
	}
	

	@Transient
	public String getNomComplet() {
		return prenom +  " " + nom;
	}

	public void setNomComplet(String nomComplet) {
	}




	/**
	 * Constructeur
	 *
	 */
	public Utilisateur() {
		super();
	}

	@Id
	@GeneratedValue
	public Long getIdUtilisateur() {
		return idUtilisateur;
	}

	/**
	 * Numero d'identification gendarmerie.
	 *
	 * @param idUtilisateur
	 *            parametre
	 */
	public void setIdUtilisateur(Long idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}



	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Date getDateMaj() {
		return dateMaj;
	}

	public void setDateMaj(Date dateMaj) {
		this.dateMaj = dateMaj;
	}

	

	/**
	 * Surcharge de la méthode equals de Object. Définie deux objet qui ont le
	 * même idUtilisateur comme étant égaux.
	 *
	 *
	 * L'objet à comparer.
	 *
	 * @return resultat de la comparaison.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result;
		if (idUtilisateur != null) {
			result = idUtilisateur.hashCode();
		}
		return result;
	}

	/**
	 * Surcharge de la méthode hashCode de Object.
	 *
	 * @param obj
	 *            parametre
	 * @return le hashCode généré.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Utilisateur other = (Utilisateur) obj;
		if (idUtilisateur == null) {
			if (other.idUtilisateur != null) {
				return false;
			}
		} else if (!idUtilisateur.equals(other.idUtilisateur)) {
			return false;
		}
		return true;
	}

	

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	

	/**
	 * Conversion en string de l'utilisateur
	 * @return string l'utilisateur
	 */
	public String toString() {
		String str =  this.nom + " " + this.prenom;
		return str;
	}

	public boolean isInitPass() {
		return initPass;
	}

	public void setInitPass(boolean initPass) {
		this.initPass = initPass;
	}

	

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getDiplome_ac() {
		return diplome_ac;
	}

	public void setDiplome_ac(String diplome_ac) {
		this.diplome_ac = diplome_ac;
	}

	public String getDiplome_p() {
		return diplome_p;
	}

	public void setDiplome_p(String diplome_p) {
		this.diplome_p = diplome_p;
	}

	
}

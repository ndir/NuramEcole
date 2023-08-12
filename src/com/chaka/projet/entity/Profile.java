package com.chaka.projet.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.Length;

import com.chaka.constantes.Constantes;


@Entity
@Table(name = "lstprofile")
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class Profile implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 5768081131650846552L;

	/**
	 * Clé primaire
	 */
	private Long idProfile;

	/**
	 * Libelle
	 */
	private String libelle;
	
	@Length(max = Constantes.LONGUEUR_LIBELLE, message="X")
	@Column(length = Constantes.LONGUEUR_LIBELLE)
	private String libelle_court;
	
	private boolean actif;
	
	private List<DroitUserAccess> listeDroit =new ArrayList<DroitUserAccess>();

	

	/**
	 * Constructeur
	 */
	public Profile() {
		super();
	}

	/**
	 * Accesseur de la variable idCivilite de type Long
	 *
	 * @return le idCivilite
	 */
	@Id
	@GeneratedValue
	public Long getIdProfile() {
		return this.idProfile;
	}

	/**
	 * Modificateur de la variable idProfile de type Long
	 */
	public void setIdProfile(Long idProfile) {
		this.idProfile = idProfile;
	}

	
	public String getLibelle_court() {
		return libelle_court;
	}

	public void setLibelle_court(String libelle_court) {
		this.libelle_court = libelle_court;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	/**
	 * Surcharge de la méthode hashCode de Object.
	 *
	 * @return le hashCode généré.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result;
		if (!(this.idProfile == null)) {
			result = result + this.idProfile.hashCode();
		}
		return result;
	}

	/**
	 * Surcharge de la méthode equals de Object. Définie deux objet qui ont le
	 * même idSexe comme étant égaux.
	 *
	 * @param obj
	 *            L'objet à comparer.
	 * @return resultat de la comparaison.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final Profile other = (Profile) obj;
		if (this.idProfile == null) {
			if (other.idProfile != null) {
				return false;
			}
		} else if (!this.idProfile.equals(other.idProfile)) {
			return false;
		}
		return true;
	}

	public boolean isActif() {
		return actif;
	}

	public void setActif(boolean actif) {
		this.actif = actif;
	}
	@OneToMany
	@JoinColumn(name = "idProfile", nullable =true)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	public List<DroitUserAccess> getListeDroit() {
		return listeDroit;
	}

	public void setListeDroit(List<DroitUserAccess> listeDroit) {
		this.listeDroit = listeDroit;
	}

}
